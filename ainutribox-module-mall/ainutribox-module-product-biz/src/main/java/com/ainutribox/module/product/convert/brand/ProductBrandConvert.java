package com.ainutribox.module.product.convert.brand;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.module.product.controller.admin.brand.vo.ProductBrandCreateReqVO;
import com.ainutribox.module.product.controller.admin.brand.vo.ProductBrandRespVO;
import com.ainutribox.module.product.controller.admin.brand.vo.ProductBrandSimpleRespVO;
import com.ainutribox.module.product.controller.admin.brand.vo.ProductBrandUpdateReqVO;
import com.ainutribox.module.product.dal.dataobject.brand.ProductBrandDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 品牌 Convert
 *
 * @author 河南小泉山科技
 */
@Mapper
public interface ProductBrandConvert {

    ProductBrandConvert INSTANCE = Mappers.getMapper(ProductBrandConvert.class);

    ProductBrandDO convert(ProductBrandCreateReqVO bean);

    ProductBrandDO convert(ProductBrandUpdateReqVO bean);

    ProductBrandRespVO convert(ProductBrandDO bean);

    List<ProductBrandSimpleRespVO> convertList1(List<ProductBrandDO> list);

    List<ProductBrandRespVO> convertList(List<ProductBrandDO> list);

    PageResult<ProductBrandRespVO> convertPage(PageResult<ProductBrandDO> page);

}
