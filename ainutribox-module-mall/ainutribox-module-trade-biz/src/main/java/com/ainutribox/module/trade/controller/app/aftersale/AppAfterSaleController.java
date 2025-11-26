package com.ainutribox.module.trade.controller.app.aftersale;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.security.core.annotations.PreAuthenticated;
import com.ainutribox.module.trade.controller.admin.aftersale.vo.log.AfterSaleLogRespVO;
import com.ainutribox.module.trade.controller.app.aftersale.vo.AppAfterSaleCreateAllReqVO;
import com.ainutribox.module.trade.controller.app.aftersale.vo.AppAfterSaleCreateReqVO;
import com.ainutribox.module.trade.controller.app.aftersale.vo.AppAfterSaleDeliveryReqVO;
import com.ainutribox.module.trade.controller.app.aftersale.vo.AppAfterSaleRespVO;
import com.ainutribox.module.trade.convert.aftersale.AfterSaleConvert;
import com.ainutribox.module.trade.service.aftersale.AfterSaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.util.List;

import static com.ainutribox.framework.common.pojo.CommonResult.success;
import static com.ainutribox.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 App - 交易售后")
@RestController
@RequestMapping("/trade/after-sale")
@Validated
@Slf4j
public class AppAfterSaleController {

    @Resource
    private AfterSaleService afterSaleService;

    @GetMapping(value = "/page")
    @Operation(summary = "获得售后分页")
    @PreAuthenticated
    public CommonResult<PageResult<AppAfterSaleRespVO>> getAfterSalePage(PageParam pageParam) {
        return success(AfterSaleConvert.INSTANCE.convertPage02(
                afterSaleService.getAfterSalePage(getLoginUserId(), pageParam)));
    }

    @GetMapping(value = "/get")
    @Operation(summary = "获得售后订单")
    @Parameter(name = "id", description = "售后编号", required = true, example = "1")
    @PreAuthenticated
    public CommonResult<AppAfterSaleRespVO> getAfterSale(@RequestParam("id") Long id) {
        return success(AfterSaleConvert.INSTANCE.convert(afterSaleService.getAfterSale(getLoginUserId(), id)));
    }

    @PostMapping(value = "/create")
    @Operation(summary = "申请售后")
    @PreAuthenticated
    public CommonResult<Long> createAfterSale(@RequestBody AppAfterSaleCreateReqVO createReqVO) {
        return success(afterSaleService.createAfterSale(getLoginUserId(), createReqVO));
    }

    @PutMapping(value = "/delivery")
    @Operation(summary = "退回货物")
    @PreAuthenticated
    public CommonResult<Boolean> deliveryAfterSale(@RequestBody AppAfterSaleDeliveryReqVO deliveryReqVO) {
        afterSaleService.deliveryAfterSale(getLoginUserId(), deliveryReqVO);
        return success(true);
    }

    @DeleteMapping(value = "/cancel")
    @Operation(summary = "取消售后")
    @Parameter(name = "id", description = "售后编号", required = true, example = "1")
    @PreAuthenticated
    public CommonResult<Boolean> cancelAfterSale(@RequestParam("id") Long id) {
        afterSaleService.cancelAfterSale(getLoginUserId(), id);
        return success(true);
    }

    @PostMapping(value = "/createAll")
    @Operation(summary = "申请订单下全部商品售后")
    @PreAuthenticated
    public CommonResult<List<Long>> createAfterSaleAll(@RequestBody AppAfterSaleCreateAllReqVO createAllReqVO) {
        return success(afterSaleService.createAfterSaleAll(getLoginUserId(), createAllReqVO));
    }


    @DeleteMapping(value = "/cancelAll")
    @Operation(summary = "取消售后(订单下全部商品)")
    @Parameter(name = "orderId", description = "订单编号", required = true, example = "1")
    @PreAuthenticated
    public CommonResult<Boolean> cancelAfterSaleAll(@RequestParam("orderId") Long orderId) {
        afterSaleService.cancelAfterSaleAll(getLoginUserId(), orderId);
        return success(true);
    }

}
