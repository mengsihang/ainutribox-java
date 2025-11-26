package com.ainutribox.module.member.controller.app.question;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.security.core.annotations.PreAuthenticated;
import com.ainutribox.module.member.controller.app.question.vo.AppQuestionRespVo;
import com.ainutribox.module.member.dal.dataobject.question.QuestionDO;
import com.ainutribox.module.member.service.question.AppQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ainutribox.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 用户题库")
@RestController
@RequestMapping("/member/question")
@Validated
public class AppQuestionController {

    @Resource
    private AppQuestionService questionAppService;

    @GetMapping("/get")
    @Operation(summary = "获得题库")
    @PreAuthenticated
    public CommonResult<List<AppQuestionRespVo>> getQuestion() {
        return success(questionAppService.getQuestion());
    }

}
