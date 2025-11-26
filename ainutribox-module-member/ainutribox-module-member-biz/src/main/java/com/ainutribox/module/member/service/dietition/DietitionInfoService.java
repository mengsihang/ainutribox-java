package com.ainutribox.module.member.service.dietition;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.dietition.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionInfoDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 营养师信息 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface DietitionInfoService {

    /**
     * 创建营养师信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDietitionInfo(@Valid DietitionInfoSaveReqVO createReqVO);

    /**
     * 更新营养师信息
     *
     * @param updateReqVO 更新信息
     */
    void updateDietitionInfo(@Valid DietitionInfoSaveReqVO updateReqVO);

    /**
     * 删除营养师信息
     *
     * @param id 编号
     */
    void deleteDietitionInfo(Long id);

    /**
     * 获得营养师信息
     *
     * @param id 编号
     * @return 营养师信息
     */
    DietitionInfoDO getDietitionInfo(Long id);

    /**
     * 获得营养师信息分页
     *
     * @param pageReqVO 分页查询
     * @return 营养师信息分页
     */
    PageResult<DietitionInfoDO> getDietitionInfoPage(DietitionInfoPageReqVO pageReqVO);


    /**
     * 通过userId获取营养师信息
     * @param userId
     * @return
     */
    DietitionInfoDO getDietitionByUserId(Long userId);

}
