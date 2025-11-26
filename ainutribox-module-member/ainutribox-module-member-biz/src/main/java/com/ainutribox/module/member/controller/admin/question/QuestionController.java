package com.ainutribox.module.member.controller.admin.question;

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

import com.ainutribox.module.member.controller.admin.question.vo.*;
import com.ainutribox.module.member.dal.dataobject.question.QuestionDO;
import com.ainutribox.module.member.service.question.QuestionService;

@Tag(name = "管理后台 - 用户题库")
@RestController
@RequestMapping("/member/question")
@Validated
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @PostMapping("/create")
    @Operation(summary = "创建用户题库")
    @PreAuthorize("@ss.hasPermission('member:question:create')")
    public CommonResult<Long> createQuestion(@Valid @RequestBody QuestionSaveReqVO createReqVO) {
        return success(questionService.createQuestion(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户题库")
    @PreAuthorize("@ss.hasPermission('member:question:update')")
    public CommonResult<Boolean> updateQuestion(@Valid @RequestBody QuestionSaveReqVO updateReqVO) {
        questionService.updateQuestion(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户题库")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:question:delete')")
    public CommonResult<Boolean> deleteQuestion(@RequestParam("id") Long id) {
        questionService.deleteQuestion(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户题库")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:question:query')")
    public CommonResult<QuestionRespVO> getQuestion(@RequestParam("id") Long id) {
        QuestionDO question = questionService.getQuestion(id);
        return success(BeanUtils.toBean(question, QuestionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户题库分页")
    @PreAuthorize("@ss.hasPermission('member:question:query')")
    public CommonResult<PageResult<QuestionRespVO>> getQuestionPage(@Valid QuestionPageReqVO pageReqVO) {
        PageResult<QuestionDO> pageResult = questionService.getQuestionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, QuestionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户题库 Excel")
    @PreAuthorize("@ss.hasPermission('member:question:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportQuestionExcel(@Valid QuestionPageReqVO pageReqVO,
                                    HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<QuestionDO> list = questionService.getQuestionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户题库.xls", "数据", QuestionRespVO.class,
                BeanUtils.toBean(list, QuestionRespVO.class));
    }

}