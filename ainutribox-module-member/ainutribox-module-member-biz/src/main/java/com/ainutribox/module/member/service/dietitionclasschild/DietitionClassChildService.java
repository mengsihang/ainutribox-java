package com.ainutribox.module.member.service.dietitionclasschild;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.dietitionclasschild.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietitionclasschild.DietitionClassChildDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 课程小节 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface DietitionClassChildService {

    /**
     * 创建课程小节
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDietitionClassChild(@Valid DietitionClassChildSaveReqVO createReqVO);

    /**
     * 更新课程小节
     *
     * @param updateReqVO 更新信息
     */
    void updateDietitionClassChild(@Valid DietitionClassChildSaveReqVO updateReqVO);

    /**
     * 删除课程小节
     *
     * @param id 编号
     */
    void deleteDietitionClassChild(Long id);

    /**
     * 获得课程小节
     *
     * @param id 编号
     * @return 课程小节
     */
    DietitionClassChildDO getDietitionClassChild(Long id);

    /**
     * 获得课程小节分页
     *
     * @param pageReqVO 分页查询
     * @return 课程小节分页
     */
    PageResult<DietitionClassChildDO> getDietitionClassChildPage(DietitionClassChildPageReqVO pageReqVO);

}