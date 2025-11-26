package com.ainutribox.module.member.job.vip;

import cn.hutool.core.util.ObjectUtil;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.quartz.core.handler.JobHandler;
import com.ainutribox.framework.tenant.core.job.TenantJob;
import com.ainutribox.module.member.dal.dataobject.user.MemberUserDO;
import com.ainutribox.module.member.dal.dataobject.vip.MemberVipDO;
import com.ainutribox.module.member.dal.mysql.user.MemberUserMapper;
import com.ainutribox.module.member.dal.mysql.vip.MemberVipMapper;
import com.ainutribox.module.member.service.order.AppMemberOrderService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 交易订单的自动过期 Job
 *
 * @author 河南小泉山科技
 */
@Component
public class MemberVipAutoCancelJob implements JobHandler {

    @Resource
    private MemberVipMapper memberVipMapper;

    @Resource
    private MemberUserMapper memberUserMapper;

    @Override
    @TenantJob
    public String execute(String param) {

        LambdaQueryWrapperX<MemberVipDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.lt(MemberVipDO::getVipEndTime, LocalDateTime.now());

        List<MemberVipDO> memberVipDOList = memberVipMapper.selectList(queryWrapper);
        List<Long> idList = memberVipDOList.stream()
                .map(MemberVipDO::getUserId)
                .toList();

        int count = 0;
        if(ObjectUtil.isNotEmpty(idList)){
            MemberUserDO userDO = new MemberUserDO();
            userDO.setVipStatus(0);
            memberUserMapper.update(userDO,new LambdaUpdateWrapper<>(MemberUserDO.class)
                    .in(MemberUserDO::getId, idList));
            count = idList.size();
        }

        return String.format("过期VIP %s 个", count);
    }

}
