package com.ainutribox.module.pay.api.refund;

import com.ainutribox.module.pay.api.refund.dto.PayRefundCreateReqDTO;
import com.ainutribox.module.pay.api.refund.dto.PayRefundRespDTO;
import com.ainutribox.module.pay.convert.refund.PayRefundConvert;
import com.ainutribox.module.pay.service.refund.PayRefundService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;

/**
 * 退款单 API 实现类
 *
 * @author 河南小泉山科技
 */
@Service
@Validated
public class PayRefundApiImpl implements PayRefundApi {

    @Resource
    private PayRefundService payRefundService;

    @Override
    public Long createRefund(PayRefundCreateReqDTO reqDTO) {
        return payRefundService.createPayRefund(reqDTO);
    }

    @Override
    public PayRefundRespDTO getRefund(Long id) {
        return PayRefundConvert.INSTANCE.convert02(payRefundService.getRefund(id));
    }

}
