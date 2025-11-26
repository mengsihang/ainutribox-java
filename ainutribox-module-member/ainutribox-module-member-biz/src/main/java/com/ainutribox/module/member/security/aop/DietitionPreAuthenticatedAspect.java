package com.ainutribox.module.member.security.aop;

import com.ainutribox.framework.security.core.util.SecurityFrameworkUtils;
import com.ainutribox.module.member.security.annotations.DietitionPreAuthenticated;
import com.ainutribox.module.member.service.user.MemberUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.ainutribox.framework.common.exception.enums.GlobalErrorCodeConstants.FORBIDDEN;
import static com.ainutribox.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;
import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;

@Aspect
@Slf4j
@Component
public class DietitionPreAuthenticatedAspect {

    @Resource
    private MemberUserService memberUserService;
    @Around("@annotation(dietitionPreAuthenticated)")
    public Object around(ProceedingJoinPoint joinPoint, DietitionPreAuthenticated dietitionPreAuthenticated) throws Throwable {
        if (SecurityFrameworkUtils.getLoginUser() == null) {
            throw exception(UNAUTHORIZED);
        }
        Long userId = SecurityFrameworkUtils.getLoginUser().getId();
        if(memberUserService.isMemberDietitionExpired(userId) != 1){
            throw exception(FORBIDDEN);
        }

        return joinPoint.proceed();
    }

}
