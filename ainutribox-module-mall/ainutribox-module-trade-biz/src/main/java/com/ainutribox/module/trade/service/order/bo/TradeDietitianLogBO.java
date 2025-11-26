package com.ainutribox.module.trade.service.order.bo;

import lombok.Data;

/**
 * 营养师交易订单日志 BO
 *
 * @author lucifer
 * @date 2024-06-25 14:13
 */

@Data
public class TradeDietitianLogBO {

    private int payPrice;
    private Long dietitianId;
    private Long dietitianSpuId;
    private String dietitianSpuName;

    public TradeDietitianLogBO(int payPrice, Long dietitianId, Long dietitianSpuId,String dietitianSpuName) {
        this.payPrice = payPrice;
        this.dietitianId = dietitianId;
        this.dietitianSpuId = dietitianSpuId;
        this.dietitianSpuName = dietitianSpuName;
    }

}
