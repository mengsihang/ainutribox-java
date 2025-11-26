package com.ainutribox.module.product.service.dietitian;

import com.ainutribox.module.product.controller.admin.spu.vo.ProductSpuUpdateStatusReqVO;
import com.ainutribox.module.product.dal.dataobject.dietitian.DietitianDO;

import java.util.List;

public interface AppDietitianService {

    List<DietitianDO> getDietitianList(Long userId,Integer type);


    /**
     * 更新营养师自组包状态 上架 下架
     *
     * @param updateReqVO 更新请求 因为和spu传入字段一直 不再新创建VO
     */
    void updateSpuStatus(ProductSpuUpdateStatusReqVO updateReqVO);
}
