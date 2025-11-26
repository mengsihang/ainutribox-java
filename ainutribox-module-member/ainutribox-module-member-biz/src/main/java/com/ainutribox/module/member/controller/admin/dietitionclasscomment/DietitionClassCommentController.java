package com.ainutribox.module.member.controller.admin.dietitionclasscomment;

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

import com.ainutribox.module.member.controller.admin.dietitionclasscomment.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietitionclasscomment.DietitionClassCommentDO;
import com.ainutribox.module.member.service.dietitionclasscomment.DietitionClassCommentService;

@Tag(name = "管理后台 - 营养师课程评论")
@RestController
@RequestMapping("/member/dietition-class-comment")
@Validated
public class DietitionClassCommentController {

    @Resource
    private DietitionClassCommentService dietitionClassCommentService;

    @PostMapping("/create")
    @Operation(summary = "创建营养师课程评论")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-comment:create')")
    public CommonResult<Long> createDietitionClassComment(@Valid @RequestBody DietitionClassCommentSaveReqVO createReqVO) {
        return success(dietitionClassCommentService.createDietitionClassComment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新营养师课程评论")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-comment:update')")
    public CommonResult<Boolean> updateDietitionClassComment(@Valid @RequestBody DietitionClassCommentSaveReqVO updateReqVO) {
        dietitionClassCommentService.updateDietitionClassComment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除营养师课程评论")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:dietition-class-comment:delete')")
    public CommonResult<Boolean> deleteDietitionClassComment(@RequestParam("id") Long id) {
        dietitionClassCommentService.deleteDietitionClassComment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得营养师课程评论")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-comment:query')")
    public CommonResult<DietitionClassCommentRespVO> getDietitionClassComment(@RequestParam("id") Long id) {
        DietitionClassCommentDO dietitionClassComment = dietitionClassCommentService.getDietitionClassComment(id);
        return success(BeanUtils.toBean(dietitionClassComment, DietitionClassCommentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得营养师课程评论分页")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-comment:query')")
    public CommonResult<PageResult<DietitionClassCommentRespVO>> getDietitionClassCommentPage(@Valid DietitionClassCommentPageReqVO pageReqVO) {
        PageResult<DietitionClassCommentDO> pageResult = dietitionClassCommentService.getDietitionClassCommentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DietitionClassCommentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出营养师课程评论 Excel")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-comment:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDietitionClassCommentExcel(@Valid DietitionClassCommentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DietitionClassCommentDO> list = dietitionClassCommentService.getDietitionClassCommentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "营养师课程评论.xls", "数据", DietitionClassCommentRespVO.class,
                        BeanUtils.toBean(list, DietitionClassCommentRespVO.class));
    }

}