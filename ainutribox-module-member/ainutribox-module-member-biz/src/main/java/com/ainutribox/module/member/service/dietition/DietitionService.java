package com.ainutribox.module.member.service.dietition;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.dietition.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 营养师记录 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface DietitionService {

    /**
     * 创建营养师记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDietition(@Valid DietitionSaveReqVO createReqVO);

    /**
     * 更新营养师记录
     *
     * @param updateReqVO 更新信息
     */
    void updateDietition(@Valid DietitionSaveReqVO updateReqVO);

    /**
     * 删除营养师记录
     *
     * @param id 编号
     */
    void deleteDietition(Long id);

    /**
     * 获得营养师记录
     *
     * @param id 编号
     * @return 营养师记录
     */
    DietitionDO getDietition(Long id);

    /**
     * 获得营养师记录
     *
     * @param id 编号
     * @return 营养师记录
     */
    DietitionDO getDietitionByUserId(Long id);

    /**
     * 获得营养师记录分页
     *
     * @param pageReqVO 分页查询
     * @return 营养师记录分页
     */
    PageResult<DietitionDO> getDietitionPage(DietitionPageReqVO pageReqVO);

}