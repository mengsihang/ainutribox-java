package com.ainutribox.module.trade.enums.dietitian;

import cn.hutool.core.util.ArrayUtil;
import com.ainutribox.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 快递配送计费方式枚举
 *
 * @author jason
 */
@AllArgsConstructor
@Getter
public enum TradeDietitianEnum implements IntArrayValuable {

    USER(0, "用户自组"),
    SPU(1,"正常商品"),
    DIETITIAN(2, "老师自组包"),
    CONSULT(3,"咨询套餐包"),
    PRODUCTTYPEZERO(0,"正常商品"),
    PROCUCTTUPEONE(1,"咨询商品"),
    ;
    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(TradeDietitianEnum::getType).toArray();

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 描述
     */
    private final String desc;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static TradeDietitianEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(chargeMode -> chargeMode.getType().equals(value), TradeDietitianEnum.values());
    }

}
