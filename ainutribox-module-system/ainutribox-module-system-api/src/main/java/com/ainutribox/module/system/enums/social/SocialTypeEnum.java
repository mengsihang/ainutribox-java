package com.ainutribox.module.system.enums.social;

import cn.hutool.core.util.ArrayUtil;
import com.ainutribox.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 社交平台的类型枚举
 *
 * @author 河南小泉山科技
 */
@Getter
@AllArgsConstructor
public enum SocialTypeEnum implements IntArrayValuable {

    /**
     * Gitee
     *
     * @see <a href="https://gitee.com/api/v5/oauth_doc#/">接入文档</a>
     */
    GITEE(10, "GITEE"),
    /**
     * 钉钉
     *
     * @see <a href="https://developers.dingtalk.com/document/app/obtain-identity-credentials">接入文档</a>
     */
    DINGTALK(20, "DINGTALK"),

    /**
     * 企业微信
     *
     * @see <a href="https://xkcoding.com/2019/08/06/use-justauth-integration-wechat-enterprise.html">接入文档</a>
     */
    WECHAT_ENTERPRISE(30, "WECHAT_ENTERPRISE"),
    /**
     * 微信公众平台 - 移动端 H5
     *
     * @see <a href="https://www.cnblogs.com/juewuzhe/p/11905461.html">接入文档</a>
     */
    WECHAT_MP(31, "WECHAT_MP"),
    /**
     * 微信开放平台 - 网站应用 PC 端扫码授权登录
     *
     * @see <a href="https://justauth.wiki/guide/oauth/wechat_open/#_2-申请开发者资质认证">接入文档</a>
     */
    WECHAT_OPEN(32, "WECHAT_OPEN"),
    /**
     * 微信小程序
     *
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html">接入文档</a>
     */
    WECHAT_MINI_APP(34, "WECHAT_MINI_APP"),

    /**
     * 微信分享推荐人为用户
     */
    WECHAT_MEMBER_ID(1,"WECHAT_MEMBER_ID"),

    /**
     * 微信分享推荐人为代理商
     */
    WECHAT_DISTRIBUTOR_ID(2,"WECHAT_DISTRIBUTOR_ID"),

    /**
     * 微信分享推荐人为营养师
     */
    WECHAT_NUTRITION_ID(3,"WECHAT_NUTRITION_ID"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(SocialTypeEnum::getType).toArray();

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 类型的标识
     */
    private final String source;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static SocialTypeEnum valueOfType(Integer type) {
        return ArrayUtil.firstMatch(o -> o.getType().equals(type), values());
    }

}
