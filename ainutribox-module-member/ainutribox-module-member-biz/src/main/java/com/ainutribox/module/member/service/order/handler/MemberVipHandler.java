package com.ainutribox.module.member.service.order.handler;

import com.ainutribox.module.member.dal.dataobject.order.MemberOrderDO;
import com.ainutribox.module.member.dal.dataobject.user.MemberUserDO;
import com.ainutribox.module.member.dal.dataobject.vip.MemberVipDO;
import com.ainutribox.module.member.dal.mysql.user.MemberUserMapper;
import com.ainutribox.module.member.dal.mysql.vip.MemberVipMapper;
import com.ainutribox.module.member.service.user.MemberUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MemberVipHandler
 *
 * @author lucifer
 * @date 2024-06-28 14:18
 */
@Component
public class MemberVipHandler implements MemberOrderHandler{

    @Resource
    private MemberUserService userService;

    @Resource
    private MemberUserMapper memberUserMapper;


    @Resource
    private MemberVipMapper memberVipMapper;

    @Override
    public void afterPayOrder(MemberOrderDO order) {
        if(order.getType() == 2){
            //判单购买用户是否是vip  -1没有记录 0否 1是
            Integer vipStatus = userService.isMemberVipExpired(order.getUserId());
            LocalDateTime nowTime = LocalDateTime.now();
            MemberVipDO memberVipDO = new MemberVipDO();
            memberVipDO.setUserId(order.getUserId());
            //未开通过
            if(vipStatus == -1){
                memberVipDO.setVipStartTime(nowTime);
                memberVipDO.setVipEndTime(nowTime.plusMonths(order.getDurationMonth()));
                memberVipMapper.insert(memberVipDO);
            }else if(vipStatus == 0){
                //已经过期
                memberVipDO.setVipEndTime(nowTime.plusMonths(order.getDurationMonth()));
                memberVipMapper.updateById(memberVipDO);
            }else if(vipStatus == 1){
                //没有到期
                MemberVipDO vipDO = memberVipMapper.selectOne("user_id", order.getUserId());
                memberVipDO.setVipEndTime(vipDO.getVipEndTime().plusMonths(order.getDurationMonth()));
                memberVipMapper.updateById(memberVipDO);
            }

            //更新主表信息
            MemberUserDO memberUserDO = new MemberUserDO();
            memberUserDO.setVipStatus(1);
            memberUserDO.setId(order.getUserId());
            memberUserMapper.updateById(memberUserDO);
        }

    }

}
