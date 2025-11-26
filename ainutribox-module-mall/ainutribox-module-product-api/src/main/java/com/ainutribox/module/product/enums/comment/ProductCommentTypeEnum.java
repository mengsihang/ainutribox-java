package com.ainutribox.module.product.enums.comment;

import com.ainutribox.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 商品类型
 *
 * @author wangzhs
 */
@Getter
@AllArgsConstructor
public enum ProductCommentTypeEnum implements IntArrayValuable {

    ZERO(0, "自选商品"),
    TWO(1, "营养包");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(ProductCommentTypeEnum::getScores).toArray();

    /**
     * 分类
     */
    private final Integer scores;

    /**
     * 分类名称
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
