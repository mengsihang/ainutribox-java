package com.ainutribox.module.member.controller.admin.order;

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

import com.ainutribox.module.member.controller.admin.order.vo.*;
import com.ainutribox.module.member.dal.dataobject.order.MemberOrderDO;
import com.ainutribox.module.member.service.order.MemberOrderService;

@Tag(name = "管理后台 - 用户购买课程和VIP订单")
@RestController
@RequestMapping("/member/order")
@Validated
public class MemberOrderController {

    @Resource
    private MemberOrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "创建用户购买课程和VIP订单")
    @PreAuthorize("@ss.hasPermission('member:order:create')")
    public CommonResult<Long> createOrder(@Valid @RequestBody MemberOrderSaveReqVO createReqVO) {
        return success(orderService.createOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户购买课程和VIP订单")
    @PreAuthorize("@ss.hasPermission('member:order:update')")
    public CommonResult<Boolean> updateOrder(@Valid @RequestBody MemberOrderSaveReqVO updateReqVO) {
        orderService.updateOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户购买课程和VIP订单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:order:delete')")
    public CommonResult<Boolean> deleteOrder(@RequestParam("id") Long id) {
        orderService.deleteOrder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户购买课程和VIP订单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:order:query')")
    public CommonResult<MemberOrderRespVO> getOrder(@RequestParam("id") Long id) {
        MemberOrderDO order = orderService.getOrder(id);
        return success(BeanUtils.toBean(order, MemberOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户购买课程和VIP订单分页")
    @PreAuthorize("@ss.hasPermission('member:order:query')")
    public CommonResult<PageResult<MemberOrderRespVO>> getOrderPage(@Valid MemberOrderPageReqVO pageReqVO) {
        PageResult<MemberOrderDO> pageResult = orderService.getOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MemberOrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户购买课程和VIP订单 Excel")
    @PreAuthorize("@ss.hasPermission('member:order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOrderExcel(@Valid MemberOrderPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MemberOrderDO> list = orderService.getOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户购买课程和VIP订单.xls", "数据", MemberOrderRespVO.class,
                        BeanUtils.toBean(list, MemberOrderRespVO.class));
    }

}