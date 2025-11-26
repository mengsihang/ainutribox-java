package com.ainutribox.module.mp.enums.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公众号消息自动回复的类型
 *
 * @author 河南小泉山科技
 */
@Getter
@AllArgsConstructor
public enum MpAutoReplyTypeEnum {

    SUBSCRIBE(1, "关注时回复"),
    MESSAGE(2, "收到消息回复"),
    KEYWORD(3, "关键词回复"),
    ;

    /**
     * 来源
     */
    private final Integer type;
    /**
     * 类型的名字
     */
    private final String name;

}
