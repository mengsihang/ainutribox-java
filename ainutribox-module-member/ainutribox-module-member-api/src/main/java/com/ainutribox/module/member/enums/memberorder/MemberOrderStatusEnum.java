package com.ainutribox.module.member.enums.memberorder;

import cn.hutool.core.util.ObjectUtil;
import com.ainutribox.framework.common.core.IntArrayValuable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * 课程VIP订单 - 状态
 *
 * @author Sin
 */
@RequiredArgsConstructor
@Getter
public enum MemberOrderStatusEnum implements IntArrayValuable {

    UNPAID(0, "待支付"),
    PAY_TIMEOUT(10, "超时未支付"),
    MEMBER_CANCEL(30, "买家取消");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(MemberOrderStatusEnum::getStatus).toArray();

    /**
     * 状态值
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

    // ========== 问：为什么写了很多 isXXX 和 haveXXX 的判断逻辑呢？ ==========
    // ========== 答：方便找到某一类判断，哪些业务正在使用 ==========

    /**
     * 判断指定状态，是否正处于【未付款】状态
     *
     * @param status 指定状态
     * @return 是否
     */
    public static boolean isUnpaid(Integer status) {
        return ObjectUtil.equal(UNPAID.getStatus(), status);
    }


}
