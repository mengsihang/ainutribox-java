package com.ainutribox.module.member.convert.order;

import com.ainutribox.module.member.dal.dataobject.order.MemberOrderDO;
import com.ainutribox.module.member.framework.order.config.ClassVipOrderProperties;
import com.ainutribox.module.pay.api.order.dto.PayOrderCreateReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static com.ainutribox.framework.common.util.date.LocalDateTimeUtils.addTime;

/**
 * MemberOrderConvet
 *
 * @author lucifer
 * @date 2024-06-28 13:30
 */
@Mapper
public interface MemberOrderConvert {

    MemberOrderConvert INSTANCE = Mappers.getMapper(MemberOrderConvert.class);

    default PayOrderCreateReqDTO convert(MemberOrderDO order, ClassVipOrderProperties orderProperties) {
        PayOrderCreateReqDTO createReqDTO = new PayOrderCreateReqDTO()
                .setAppId(orderProperties.getAppId()).setUserIp(order.getUserIp());
        // 商户相关字段
        createReqDTO.setMerchantOrderId(String.valueOf(order.getId()));
        String subject = order.getBuyName();
        createReqDTO.setSubject(subject);
        createReqDTO.setBody(subject); // TODO 芋艿：临时写死
        // 订单相关字段
        createReqDTO.setPrice(order.getPayPrice()).setExpireTime(addTime(orderProperties.getPayExpireTime()));
        return createReqDTO;
    }
}
