package com.ainutribox.module.member.service.push;

import cn.hutool.core.util.ObjectUtil;
import com.ainutribox.module.member.dal.dataobject.report.ReportDO;
import com.ainutribox.module.member.dal.mysql.report.ReportMapper;
import com.ainutribox.module.member.service.report.ReportService;
import com.ainutribox.module.trade.api.push.ReportPushApi;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.DIETITION_NOT_ID_EXISTS;

/**
 * reportPushServiceImpl
 *
 * @author lucifer
 * @date 2024-11-29 14:30
 */
@Service
@Validated
@Slf4j
public class ReportPushServiceImpl implements ReportPushApi {

    @Value("${ainutribox.report.url}")
    private String reportUlr;

    @Resource
    RestTemplate restTemplate = new RestTemplate();

    @Resource
    private ReportMapper reportMapper;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");

    @Override
    public Long reportSendGenerate(List<Map<String, Object>> reportMap,Long memberUserId) {
        if(ObjectUtil.isEmpty(reportMap)){
            throw exception(DIETITION_NOT_ID_EXISTS);
        }

        String body = JSONObject.toJSONString(reportMap);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setContentLength(body.getBytes(StandardCharsets.UTF_8).length);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);

        JSONObject reportJson = restTemplate.postForObject(
                reportUlr, httpEntity, JSONObject.class);

        ReportDO reportDO = new ReportDO();
        if (reportJson != null) {
            reportDO.setReportName(formatter.format(new Date()));
            reportDO.setContent(reportJson.toJSONString());
            reportDO.setMemberId(memberUserId);
            reportMapper.insert(reportDO);
        }
        return reportDO.getId();
    }

}
