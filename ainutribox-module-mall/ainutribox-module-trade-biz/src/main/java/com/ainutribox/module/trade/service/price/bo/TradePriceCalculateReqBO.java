package com.ainutribox.module.trade.service.price.bo;

import com.ainutribox.module.trade.enums.delivery.DeliveryTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 价格计算 Request BO
 *
 * @author 河南小泉山科技
 */
@Data
public class TradePriceCalculateReqBO {

    /**
     * 用户编号
     *
     * 对应 MemberUserDO 的 id 编号
     */
    private Long userId;

    /**
     * 优惠劵编号
     *
     * 对应 CouponDO 的 id 编号
     */
    private Long couponId;

    /**
     * 是否使用积分
     */
    @NotNull(message = "是否使用积分不能为空")
    private Boolean pointStatus;

    /**
     * 配送方式
     *
     * 枚举 {@link DeliveryTypeEnum}
     */
    private Integer deliveryType;
    /**
     * 收货地址编号
     *
     * 对应 MemberAddressDO 的 id 编号
     */
    private Long addressId;
    /**
     * 自提门店编号
     *
     * 对应 PickUpStoreDO 的 id 编号
     */
    private Long pickUpStoreId;

    /**
     * 商品 SKU 数组
     */
    @NotNull(message = "商品数组不能为空")
    private List<Item> items;

    // ========== 秒杀活动相关字段 ==========
    /**
     * 秒杀活动编号
     */
    private Long seckillActivityId;

    // ========== 拼团活动相关字段 ==========
    /**
     * 拼团活动编号
     */
    private Long combinationActivityId;

    /**
     * 拼团团长编号
     */
    private Long combinationHeadId;

    // ========== 砍价活动相关字段 ==========
    /**
     * 砍价记录编号
     */
    private Long bargainRecordId;

    /**
     * 商品 SKU
     */
    @Data
    @Valid
    public static class Item {

        /**
         * SKU 编号
         */
        @NotNull(message = "商品 SKU 编号不能为空")
        private Long skuId;

        /**
         * SKU 数量
         */
        @NotNull(message = "商品 SKU 数量不能为空")
        @Min(value = 0L, message = "商品 SKU 数量必须大于等于 0")
        private Integer count;

        /**
         * 购物车项的编号
         */
        private Long cartId;

        /**
         * 是否选中
         */
        @NotNull(message = "是否选中不能为空")
        private Boolean selected;

        private String spuName;

        @Schema(description = "订单项类型 0用户自组包 1商品 2营养师组包", example = "1")
        private Integer itemType;

        @Schema(description = "营养师ID", example = "1024")
        private Long dietitianId;

        @Schema(description = "营养师组商品ID", example = "1024")
        private Long dietitianSpuId;

        @Schema(description = "组数", example = "1024")
        private Integer itemNumber;

        @Schema(description = "商品单位", example = "1024")
        private String spuUnit;


    }
}
