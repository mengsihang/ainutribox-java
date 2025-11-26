package com.ainutribox.module.member.controller.app.dietitionclass.vo;

/**
 * AppDietitionInfoVo
 *
 * @author lucifer
 * @date 2024-07-03 11:13
 */

import com.ainutribox.module.member.dal.dataobject.dietition.DietitionInfoDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 获取营养师相关信息
 */
@Schema(description = "用户 app - 老师相关信息 显示")
@Data
public class AppDietitionInfoVo {

    /**
     * 课程列表
     */
    List<AppUserClassVO> dietitionClassList;

    /**
     * 导师信息
     */
    DietitionInfoDO dietitionInfoDO;

    /**
     * 商品列表
     */
    List<DietitianProductVO> dietitianProductList;

}
