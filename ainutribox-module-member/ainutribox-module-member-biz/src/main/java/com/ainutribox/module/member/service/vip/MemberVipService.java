package com.ainutribox.module.member.service.vip;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.vip.vo.*;
import com.ainutribox.module.member.dal.dataobject.vip.MemberVipDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 用户vip Service 接口
 *
 * @author 小泉山网络科技
 */
public interface MemberVipService {

    /**
     * 创建用户vip
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVip(@Valid MemberVipSaveReqVO createReqVO);

    /**
     * 更新用户vip
     *
     * @param updateReqVO 更新信息
     */
    void updateVip(@Valid MemberVipSaveReqVO updateReqVO);

    /**
     * 删除用户vip
     *
     * @param id 编号
     */
    void deleteVip(Long id);

    /**
     * 获得用户vip
     *
     * @param id 编号
     * @return 用户vip
     */
    MemberVipDO getVip(Long id);

    /**
     * 获得用户vip分页
     *
     * @param pageReqVO 分页查询
     * @return 用户vip分页
     */
    PageResult<MemberVipDO> getVipPage(MemberVipPageReqVO pageReqVO);

}