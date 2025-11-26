package com.ainutribox.module.promotion.convert.reward;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.module.promotion.controller.admin.reward.vo.RewardActivityCreateReqVO;
import com.ainutribox.module.promotion.controller.admin.reward.vo.RewardActivityRespVO;
import com.ainutribox.module.promotion.controller.admin.reward.vo.RewardActivityUpdateReqVO;
import com.ainutribox.module.promotion.dal.dataobject.reward.RewardActivityDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 满减送活动 Convert
 *
 * @author 河南小泉山科技
 */
@Mapper
public interface RewardActivityConvert {

    RewardActivityConvert INSTANCE = Mappers.getMapper(RewardActivityConvert.class);

    RewardActivityDO convert(RewardActivityCreateReqVO bean);

    RewardActivityDO convert(RewardActivityUpdateReqVO bean);

    RewardActivityRespVO convert(RewardActivityDO bean);

    PageResult<RewardActivityRespVO> convertPage(PageResult<RewardActivityDO> page);

}
