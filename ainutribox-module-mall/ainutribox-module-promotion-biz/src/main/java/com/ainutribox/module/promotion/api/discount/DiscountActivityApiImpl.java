package com.ainutribox.module.promotion.api.discount;

import com.ainutribox.module.promotion.api.discount.dto.DiscountProductRespDTO;
import com.ainutribox.module.promotion.convert.discount.DiscountActivityConvert;
import com.ainutribox.module.promotion.service.discount.DiscountActivityService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 限时折扣 API 实现类
 *
 * @author 河南小泉山科技
 */
@Service
@Validated
public class DiscountActivityApiImpl implements DiscountActivityApi {

    @Resource
    private DiscountActivityService discountActivityService;

    @Override
    public List<DiscountProductRespDTO> getMatchDiscountProductList(Collection<Long> skuIds) {
        return DiscountActivityConvert.INSTANCE.convertList02(discountActivityService.getMatchDiscountProductList(skuIds));
    }

}
