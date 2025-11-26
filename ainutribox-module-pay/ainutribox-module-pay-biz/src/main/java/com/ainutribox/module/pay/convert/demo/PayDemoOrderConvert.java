package com.ainutribox.module.pay.convert.demo;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.module.pay.controller.admin.demo.vo.order.PayDemoOrderCreateReqVO;
import com.ainutribox.module.pay.controller.admin.demo.vo.order.PayDemoOrderRespVO;
import com.ainutribox.module.pay.dal.dataobject.demo.PayDemoOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 示例订单 Convert
 *
 * @author 河南小泉山科技
 */
@Mapper
public interface PayDemoOrderConvert {

    PayDemoOrderConvert INSTANCE = Mappers.getMapper(PayDemoOrderConvert.class);

    PayDemoOrderDO convert(PayDemoOrderCreateReqVO bean);

    PayDemoOrderRespVO convert(PayDemoOrderDO bean);

    PageResult<PayDemoOrderRespVO> convertPage(PageResult<PayDemoOrderDO> page);

}
