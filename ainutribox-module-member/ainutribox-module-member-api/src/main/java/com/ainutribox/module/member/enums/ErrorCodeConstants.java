package com.ainutribox.module.member.enums;

import com.ainutribox.framework.common.exception.ErrorCode;

/**
 * Member 错误码枚举类
 * <p>
 * member 系统，使用 1-004-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 用户相关  1-004-001-000 ============
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1_004_001_000, "用户不存在");
    ErrorCode USER_MOBILE_NOT_EXISTS = new ErrorCode(1_004_001_001, "手机号未注册用户");
    ErrorCode USER_MOBILE_USED = new ErrorCode(1_004_001_002, "修改手机失败，该手机号({})已经被使用");
    ErrorCode USER_POINT_NOT_ENOUGH = new ErrorCode(1_004_001_003, "用户积分余额不足");

    // ========== AUTH 模块 1-004-003-000 ==========
    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1_004_003_000, "登录失败，账号密码不正确");
    ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1_004_003_001, "登录失败，账号被禁用");
    ErrorCode AUTH_SOCIAL_USER_NOT_FOUND = new ErrorCode(1_004_003_005, "登录失败，解析不到三方登录信息");
    ErrorCode AUTH_MOBILE_USED = new ErrorCode(1_004_003_007, "手机号已经被使用");

    // ========== 用户收件地址 1-004-004-000 ==========
    ErrorCode ADDRESS_NOT_EXISTS = new ErrorCode(1_004_004_000, "用户收件地址不存在");

    //========== 用户标签 1-004-006-000 ==========
    ErrorCode TAG_NOT_EXISTS = new ErrorCode(1_004_006_000, "用户标签不存在");
    ErrorCode TAG_NAME_EXISTS = new ErrorCode(1_004_006_001, "用户标签已经存在");
    ErrorCode TAG_HAS_USER = new ErrorCode(1_004_006_002, "用户标签下存在用户，无法删除");

    //========== 积分配置 1-004-007-000 ==========

    //========== 积分记录 1-004-008-000 ==========
    ErrorCode POINT_RECORD_BIZ_NOT_SUPPORT = new ErrorCode(1_004_008_000, "用户积分记录业务类型不支持");

    //========== 签到配置 1-004-009-000 ==========
    ErrorCode SIGN_IN_CONFIG_NOT_EXISTS = new ErrorCode(1_004_009_000, "签到天数规则不存在");
    ErrorCode SIGN_IN_CONFIG_EXISTS = new ErrorCode(1_004_009_001, "签到天数规则已存在");

    //========== 签到配置 1-004-010-000 ==========
    ErrorCode SIGN_IN_RECORD_TODAY_EXISTS = new ErrorCode(1_004_010_000, "今日已签到，请勿重复签到");

    //========== 用户等级 1-004-011-000 ==========
    ErrorCode LEVEL_NOT_EXISTS = new ErrorCode(1_004_011_000, "用户等级不存在");
    ErrorCode LEVEL_NAME_EXISTS = new ErrorCode(1_004_011_001, "用户等级名称[{}]已被使用");
    ErrorCode LEVEL_VALUE_EXISTS = new ErrorCode(1_004_011_002, "用户等级值[{}]已被[{}]使用");
    ErrorCode LEVEL_EXPERIENCE_MIN = new ErrorCode(1_004_011_003, "升级经验必须大于上一个等级[{}]设置的升级经验[{}]");
    ErrorCode LEVEL_EXPERIENCE_MAX = new ErrorCode(1_004_011_004, "升级经验必须小于下一个等级[{}]设置的升级经验[{}]");
    ErrorCode LEVEL_HAS_USER = new ErrorCode(1_004_011_005, "用户等级下存在用户，无法删除");

    ErrorCode EXPERIENCE_BIZ_NOT_SUPPORT = new ErrorCode(1_004_011_201, "用户经验业务类型不支持");

    //========== 用户分组 1-004-012-000 ==========
    ErrorCode GROUP_NOT_EXISTS = new ErrorCode(1_004_012_000, "用户分组不存在");
    ErrorCode GROUP_HAS_USER = new ErrorCode(1_004_012_001, "用户分组下存在用户，无法删除");
    // ========== 用户题库 1-004-013-000 ==========
    ErrorCode QUESTION_NOT_EXISTS = new ErrorCode(1_004_013_001, "用户题库不存在");
    ErrorCode QUESTION_ANSWER_NOT_EXISTS = new ErrorCode(1_004_013_002, "用户题库答案不存在");
    // ========== 用户报告 1-004-014-000==========
    ErrorCode REPORT_NOT_EXISTS = new ErrorCode(1_004_014_001, "用户报告不存在");

    // ========== 地址分析 1_004_015_000 ==========
    ErrorCode ADDRESS_NULL_RES = new ErrorCode(1_020_015_001, "解析调用返回数据为空");

    ErrorCode ADDRESS_NOT_ZERO_RES = new ErrorCode(1_020_015_002, "");

    ErrorCode ADDRESS_NULL_STR = new ErrorCode(1_020_015_003, "地址不能为空");

    // ========== 积分兑换码 1-006-010-000 ==========
    ErrorCode CODE_NOT_EXISTS = new ErrorCode(1_006_010_000, "积分兑换码不存在");
    ErrorCode CODE_IS_EXISTS = new ErrorCode(1_006_010_001, "积分兑换码已存在");

    // ========== 代理商记录 1-007-001-000 ==========
    ErrorCode AGENT_NOT_EXISTS = new ErrorCode(1_007_001_000,  "代理商记录不存在");
    ErrorCode AGENT_STATUS_ZERO = new ErrorCode(1_007_001_001,"审核中");
    ErrorCode AGENT_STATUS_OTHER = new ErrorCode(1_007_001_002,"已存在代理商记录");
    ErrorCode AGENT_STATUS_ONE = new ErrorCode(1_007_001_003,"审核已完成");

    // ========== 营养师记录 1-007-002-000 ==========
    ErrorCode DIETITION_NOT_EXISTS = new ErrorCode(1_007_002_000, "营养师记录不存在");
    ErrorCode DIETITION_STATUS_ZERO = new ErrorCode(1_007_002_001,"审核中");
    ErrorCode DIETITION_STATUS_OTHER = new ErrorCode(1_007_002_002,"已存在营养师记录");
    ErrorCode DIETITION_STATUS_ONE = new ErrorCode(1_007_002_003,"审核已完成");

    // ========== 营养师记录 1-008-001-000 ==========
    ErrorCode SHAREQR_STATUS_OTHER = new ErrorCode(1_008_001_000,"生成二维码失败");

    // ========== 营养师信息 1-007-003-000 ==========
    ErrorCode DIETITION_INFO_NOT_EXISTS = new ErrorCode(1_007_003_000, "营养师信息不存在");


    // ========== 营养师课程 1-008-000-000 ==========
    ErrorCode DIETITION_CLASS_NOT_EXISTS = new ErrorCode(1_008_000_000, "营养师课程不存在");
    ErrorCode DIETITION_CLASS_CATEGORY_NOT_EXISTS = new ErrorCode(1_008_000_001, "营养师课程分类不存在");
    ErrorCode DIETITION_CLASS_CHILD_NOT_EXISTS = new ErrorCode(1_008_000_002,"小节不存在");

    // ========== 用户vip和课程订单 1-008-000-000 ==========
    ErrorCode ORDER_NOT_EXISTS = new ErrorCode(1_009_000_000, "订单不存在");
    ErrorCode VIP_PACKAGE_NOT_EXISTS = new ErrorCode(1_009_000_001, "vip套餐不存在");
    ErrorCode VIP_PACKAGE_STATUS_DOWN = new ErrorCode(1_009_000_002, "vip套餐已下架");
    ErrorCode VIP_NOT_EXISTS = new ErrorCode(1_009_000_003, "vip不存在");
    ErrorCode USER_PAY_CLASS = new ErrorCode(1_009_000_004,"该课程已购买");
    ErrorCode PAY_CLASS_NOT_EXISTS = new ErrorCode(1_009_000_005,"课程不存在");
    ErrorCode PAY_CLASS_STATUS_EXISTS = new ErrorCode(1_009_000_006,"无法购买该课程,可能下架或删除");
    ErrorCode JOIN_CLASS_NOT_EXISTS = new ErrorCode(1_009_000_007,"用户加入课程记录不存在");
    ErrorCode CLASS_TYPE_ERROR = new ErrorCode(1_009_000_008,"操作类型不匹配");
    ErrorCode CLASS_FREQUENTLY_ERROR = new ErrorCode(1_009_000_009,"操作频繁");
    ErrorCode JOIN_CLASS_EXISTS = new ErrorCode(1_009_000_010,"已加入课程");
    ErrorCode NOT_JOIN_CLASS_EXISTS = new ErrorCode(1_009_000_011,"未加入该课程");
    ErrorCode SPOT_CLASS_NOT_EXISTS = new ErrorCode(1_009_000_012,"点赞记录不存在");
    ErrorCode SPOT_CLASS_EXISTS = new ErrorCode(1_009_000_012,"已经点赞啦");
    ErrorCode DIETITION_CLASS_COMMENT_NOT_EXISTS = new ErrorCode(1_009_000_013,"评论不存在");
    ErrorCode DIETITION_NOT_ID_EXISTS = new ErrorCode(1_009_000_014,"导师ID不能为空");

    ErrorCode REPORT_NOT_DATA = new ErrorCode(1_009_000_015,"报告传入数据不能为空");

    // ========== 用户阅读记录 1-010-000-000 ==========
    ErrorCode READ_CLASS_NOT_EXISTS = new ErrorCode(1_010_000_001, "用户阅读记录不存在");


}
