package com.ainutribox.module.member.controller.app.config;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.module.member.controller.admin.config.vo.MemberConfigRespVO;
import com.ainutribox.module.member.convert.config.MemberConfigConvert;
import com.ainutribox.module.member.dal.dataobject.config.MemberConfigDO;
import com.ainutribox.module.member.service.config.MemberConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.ainutribox.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 获取积分配置")
@RestController
@RequestMapping("/member/config")
@Validated
@Slf4j
public class AppMemberConfigController {

    @Resource
    private MemberConfigService memberConfigService;

    @GetMapping("/get")
    @Operation(summary = "获得会员配置")
    public CommonResult<MemberConfigRespVO> getConfig() {
        MemberConfigDO config = memberConfigService.getConfig();
        return success(MemberConfigConvert.INSTANCE.convert(config));
    }

}
