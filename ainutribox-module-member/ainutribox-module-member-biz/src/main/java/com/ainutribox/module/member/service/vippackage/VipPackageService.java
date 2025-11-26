package com.ainutribox.module.member.service.vippackage;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.vippackage.vo.*;
import com.ainutribox.module.member.dal.dataobject.vippackage.VipPackageDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * vip套餐 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface VipPackageService {

    /**
     * 创建vip套餐
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVipPackage(@Valid VipPackageSaveReqVO createReqVO);

    /**
     * 更新vip套餐
     *
     * @param updateReqVO 更新信息
     */
    void updateVipPackage(@Valid VipPackageSaveReqVO updateReqVO);

    /**
     * 删除vip套餐
     *
     * @param id 编号
     */
    void deleteVipPackage(Long id);

    /**
     * 获得vip套餐
     *
     * @param id 编号
     * @return vip套餐
     */
    VipPackageDO getVipPackage(Long id);

    /**
     * 获得vip套餐分页
     *
     * @param pageReqVO 分页查询
     * @return vip套餐分页
     */
    PageResult<VipPackageDO> getVipPackagePage(VipPackagePageReqVO pageReqVO);

}