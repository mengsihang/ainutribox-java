package com.ainutribox.module.product.enums.spu;

import com.ainutribox.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 商品 SPU 状态
 *
 * @author 河南小泉山科技
 */
@Getter
@AllArgsConstructor
public enum DietitianSpuStatusEnum implements IntArrayValuable {

    USER(2, "用户"),
    Dietitian(1, "营养师");


    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DietitianSpuStatusEnum::getStatus).toArray();

    /**
     * 状态
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
