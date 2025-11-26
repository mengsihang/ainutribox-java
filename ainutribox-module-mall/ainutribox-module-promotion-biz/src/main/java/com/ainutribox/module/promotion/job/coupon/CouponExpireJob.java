package com.ainutribox.module.promotion.job.coupon;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ainutribox.framework.quartz.core.handler.JobHandler;
import com.ainutribox.framework.tenant.core.job.TenantJob;
import com.ainutribox.module.promotion.dal.dataobject.coupon.CouponDO;
import com.ainutribox.module.promotion.dal.mysql.coupon.CouponMapper;
import com.ainutribox.module.promotion.enums.coupon.CouponStatusEnum;
import com.ainutribox.module.promotion.service.coupon.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;

// TODO 芋艿：配置一个 Job
/**
 * 优惠券过期 Job
 *
 * @author owen
 */
@Component
@Slf4j
public class CouponExpireJob implements JobHandler {

    @Resource
    private CouponService couponService;

    @Resource
    private CouponMapper couponMapper;

    @Override
    @TenantJob
    public String execute(String param) {
        // 1. 查询待过期的优惠券
        List<CouponDO> list = couponMapper.selectListByStatusAndValidEndTimeLe(
                CouponStatusEnum.UNUSED.getStatus(), LocalDateTime.now());
        if (CollUtil.isEmpty(list)) {
            StrUtil.format("过期优惠券 {} 个", 0);
        }

        // 2. 遍历执行
        int count = 0;
        for (CouponDO coupon : list) {
            try {
                boolean success = expireCoupon(coupon);
                if (success) {
                    count++;
                }
            } catch (Exception e) {
                log.error("[expireCoupon][coupon({}) 更新为已过期失败]", coupon.getId(), e);
            }
        }
        return StrUtil.format("过期优惠券 {} 个", count);
    }
    private boolean expireCoupon(CouponDO coupon) {
        // 更新记录状态
        int updateRows = couponMapper.updateByIdAndStatus(coupon.getId(), CouponStatusEnum.UNUSED.getStatus(),
                new CouponDO().setStatus(CouponStatusEnum.EXPIRE.getStatus()));
        if (updateRows == 0) {
            log.error("[expireCoupon][coupon({}) 更新为已过期失败]", coupon.getId());
            return false;
        }
        log.info("[expireCoupon][coupon({}) 更新为已过期成功]", coupon.getId());
        return true;
    }

}
