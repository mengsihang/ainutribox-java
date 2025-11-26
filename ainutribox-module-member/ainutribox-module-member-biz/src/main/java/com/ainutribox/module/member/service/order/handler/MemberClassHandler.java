package com.ainutribox.module.member.service.order.handler;

import com.ainutribox.module.member.dal.dataobject.dietitionclass.DietitionClassDO;
import com.ainutribox.module.member.dal.dataobject.joinclass.JoinClassDO;
import com.ainutribox.module.member.dal.dataobject.order.MemberOrderDO;
import com.ainutribox.module.member.dal.dataobject.payclass.PayClassDO;
import com.ainutribox.module.member.dal.mysql.dietitionclass.DietitionClassMapper;
import com.ainutribox.module.member.dal.mysql.joinclass.JoinClassMapper;
import com.ainutribox.module.member.dal.mysql.payclass.PayClassMapper;
import com.ainutribox.module.member.service.dietitionclass.AppDietitionClassService;
import com.ainutribox.module.member.service.joinclass.JoinClassService;
import com.ainutribox.module.member.service.payclass.PayClassService;
import com.ainutribox.module.member.service.user.MemberUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * MemberClassHandler
 *
 * @author lucifer
 * @date 2024-06-28 14:18
 */
@Component
public class MemberClassHandler implements MemberOrderHandler{

    @Resource
    private MemberUserService userService;

    @Resource
    private JoinClassMapper joinClassMapper;

    @Resource
    private PayClassMapper payClassMapper;

    @Resource
    private AppDietitionClassService appClassService;

    @Override
    public void afterPayOrder(MemberOrderDO order) {
        if(order.getType() == 1){
            //创建订单时已经判断过是否购买课程这边直接添加到 已够和 已加入课程 主课程人数+1需要判断是否已经加入过课程
            PayClassDO payClassDO = new PayClassDO();
            payClassDO.setOrderId(order.getId());
            payClassDO.setDietition(order.getDietitionId());
            payClassDO.setUserId(order.getUserId());
            payClassDO.setClassId(order.getBuyId());
            payClassMapper.insert(payClassDO);

            //判断是否已经加入过课程 没有加入加入 课程人数加一  加入过不需要任何操作 因为加入课程会对课程人数加1
            Integer joinStatus =  userService.isMemberJoinClassExpired(order.getUserId(), order.getBuyId());
            if(joinStatus == 0){
                JoinClassDO joinClassDO = new JoinClassDO();
                joinClassDO.setMemberId(order.getUserId());
                joinClassDO.setClassId(order.getBuyId());
                joinClassMapper.insert(joinClassDO);

                //更新课程人数
                appClassService.updateClassPeople(order.getBuyId(),1);
            }

        }


    }
}
