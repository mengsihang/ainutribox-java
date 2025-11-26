package com.ainutribox.module.product.service.dietitian;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.product.controller.admin.dietitian.vo.*;
import com.ainutribox.module.product.dal.dataobject.dietitian.DietitianDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 营养师自组营养包 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface DietitianService {

    /**
     * 创建营养师自组营养包
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDietitian(@Valid DietitianSaveReqVO createReqVO);

    /**
     * 更新营养师自组营养包
     *
     * @param updateReqVO 更新信息
     */
    void updateDietitian(@Valid DietitianSaveReqVO updateReqVO);

    /**
     * 删除营养师自组营养包
     *
     * @param id 编号
     */
    void deleteDietitian(Long id);

    /**
     * 获得营养师自组营养包
     *
     * @param id 编号
     * @return 营养师自组营养包
     */
    DietitianDO getDietitian(Long id);

    /**
     * 获得营养师自组营养包分页
     *
     * @param pageReqVO 分页查询
     * @return 营养师自组营养包分页
     */
    PageResult<DietitianDO> getDietitianPage(DietitianPageReqVO pageReqVO);

}