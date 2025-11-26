package com.ainutribox.module.member.service.dietitionclass;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.dietitionclass.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietitionclass.DietitionClassDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 营养师课程 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface DietitionClassService {

    /**
     * 创建营养师课程
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDietitionClass(@Valid DietitionClassSaveReqVO createReqVO);

    /**
     * 更新营养师课程
     *
     * @param updateReqVO 更新信息
     */
    void updateDietitionClass(@Valid DietitionClassSaveReqVO updateReqVO);

    /**
     * 删除营养师课程
     *
     * @param id 编号
     */
    void deleteDietitionClass(Long id);

    /**
     * 获得营养师课程
     *
     * @param id 编号
     * @return 营养师课程
     */
    DietitionClassDO getDietitionClass(Long id);

    /**
     * 获得营养师课程分页
     *
     * @param pageReqVO 分页查询
     * @return 营养师课程分页
     */
    PageResult<DietitionClassDO> getDietitionClassPage(DietitionClassPageReqVO pageReqVO);




}