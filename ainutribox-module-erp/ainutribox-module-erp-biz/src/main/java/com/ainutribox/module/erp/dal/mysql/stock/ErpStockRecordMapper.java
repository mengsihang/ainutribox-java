package com.ainutribox.module.erp.dal.mysql.stock;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.module.erp.controller.admin.stock.vo.record.ErpStockRecordPageReqVO;
import com.ainutribox.module.erp.dal.dataobject.stock.ErpStockRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ERP 产品库存明细 Mapper
 *
 * @author 河南小泉山科技
 */
@Mapper
public interface ErpStockRecordMapper extends BaseMapperX<ErpStockRecordDO> {

    default PageResult<ErpStockRecordDO> selectPage(ErpStockRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ErpStockRecordDO>()
                .eqIfPresent(ErpStockRecordDO::getProductId, reqVO.getProductId())
                .eqIfPresent(ErpStockRecordDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ErpStockRecordDO::getBizType, reqVO.getBizType())
                .likeIfPresent(ErpStockRecordDO::getBizNo, reqVO.getBizNo())
                .betweenIfPresent(ErpStockRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ErpStockRecordDO::getId));
    }

}