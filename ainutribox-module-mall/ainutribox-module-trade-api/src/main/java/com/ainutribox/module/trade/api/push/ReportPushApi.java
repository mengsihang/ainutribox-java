package com.ainutribox.module.trade.api.push;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * reportPush
 * 报告转发获取
 * @author lucifer
 * @date 2024-11-29 14:27
 */
public interface ReportPushApi {

  public Long reportSendGenerate(List<Map<String, Object>> reportMap,Long memberUserId);
}
