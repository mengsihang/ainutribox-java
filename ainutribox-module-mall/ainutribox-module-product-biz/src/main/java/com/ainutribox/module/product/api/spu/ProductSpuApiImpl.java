package com.ainutribox.module.product.api.spu;

import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.module.product.api.spu.dto.ProductSpuRespDTO;
import com.ainutribox.module.product.dal.dataobject.spu.ProductSpuDO;
import com.ainutribox.module.product.dal.mysql.spu.ProductSpuMapper;
import com.ainutribox.module.product.service.spu.ProductSpuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

/**
 * 商品 SPU API 接口实现类
 *
 * @author LeeYan9
 * @since 2022-09-06
 */
@Service
@Validated
public class ProductSpuApiImpl implements ProductSpuApi {

    @Resource
    private ProductSpuService spuService;

    @Resource
    private ProductSpuMapper productSpuMapper;

    @Override
    public List<ProductSpuRespDTO> getSpuList(Collection<Long> ids) {
        List<ProductSpuDO> spus = spuService.getSpuList(ids);
        return BeanUtils.toBean(spus, ProductSpuRespDTO.class);
    }

    @Override
    public List<ProductSpuRespDTO> validateSpuList(Collection<Long> ids) {
        List<ProductSpuDO> spus = spuService.validateSpuList(ids);
        return BeanUtils.toBean(spus, ProductSpuRespDTO.class);
    }

    @Override
    public ProductSpuRespDTO getSpu(Long id) {
        ProductSpuDO spu = spuService.getSpu(id);
        return BeanUtils.toBean(spu, ProductSpuRespDTO.class);
    }

    @Override
    public void updateSalesCount(Long spuId, Integer count) {
        productSpuMapper.updateSalesCount(spuId, count);
    }


}
