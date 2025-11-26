package com.ainutribox.module.member.controller.admin.questionanswer;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import static com.ainutribox.framework.common.pojo.CommonResult.success;

import com.ainutribox.framework.excel.core.util.ExcelUtils;

import com.ainutribox.framework.apilog.core.annotation.ApiAccessLog;
import static com.ainutribox.framework.apilog.core.enums.OperateTypeEnum.*;

import com.ainutribox.module.member.controller.admin.questionanswer.vo.*;
import com.ainutribox.module.member.dal.dataobject.questionanswer.QuestionAnswerDO;
import com.ainutribox.module.member.service.questionanswer.QuestionAnswerService;

@Tag(name = "管理后台 - 用户题库答案")
@RestController
@RequestMapping("/member/question-answer")
@Validated
public class QuestionAnswerController {

    @Resource
    private QuestionAnswerService questionAnswerService;

    @PostMapping("/create")
    @Operation(summary = "创建用户题库答案")
    @PreAuthorize("@ss.hasPermission('member:question-answer:create')")
    public CommonResult<Long> createQuestionAnswer(@Valid @RequestBody QuestionAnswerSaveReqVO createReqVO) {
        return success(questionAnswerService.createQuestionAnswer(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户题库答案")
    @PreAuthorize("@ss.hasPermission('member:question-answer:update')")
    public CommonResult<Boolean> updateQuestionAnswer(@Valid @RequestBody QuestionAnswerSaveReqVO updateReqVO) {
        questionAnswerService.updateQuestionAnswer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户题库答案")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:question-answer:delete')")
    public CommonResult<Boolean> deleteQuestionAnswer(@RequestParam("id") Long id) {
        questionAnswerService.deleteQuestionAnswer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户题库答案")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:question-answer:query')")
    public CommonResult<QuestionAnswerRespVO> getQuestionAnswer(@RequestParam("id") Long id) {
        QuestionAnswerDO questionAnswer = questionAnswerService.getQuestionAnswer(id);
        return success(BeanUtils.toBean(questionAnswer, QuestionAnswerRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户题库答案分页")
    @PreAuthorize("@ss.hasPermission('member:question-answer:query')")
    public CommonResult<PageResult<QuestionAnswerRespVO>> getQuestionAnswerPage(@Valid QuestionAnswerPageReqVO pageReqVO) {
        PageResult<QuestionAnswerDO> pageResult = questionAnswerService.getQuestionAnswerPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, QuestionAnswerRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户题库答案 Excel")
    @PreAuthorize("@ss.hasPermission('member:question-answer:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportQuestionAnswerExcel(@Valid QuestionAnswerPageReqVO pageReqVO,
                                          HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<QuestionAnswerDO> list = questionAnswerService.getQuestionAnswerPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户题库答案.xls", "数据", QuestionAnswerRespVO.class,
                BeanUtils.toBean(list, QuestionAnswerRespVO.class));
    }

}