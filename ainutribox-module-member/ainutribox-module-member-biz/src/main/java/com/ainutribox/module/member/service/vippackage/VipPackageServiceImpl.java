package com.ainutribox.module.member.service.vippackage;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.member.controller.admin.vippackage.vo.*;
import com.ainutribox.module.member.dal.dataobject.vippackage.VipPackageDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.member.dal.mysql.vippackage.VipPackageMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * vip套餐 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class VipPackageServiceImpl implements VipPackageService {

    @Resource
    private VipPackageMapper vipPackageMapper;

    @Override
    public Long createVipPackage(VipPackageSaveReqVO createReqVO) {
        // 插入
        VipPackageDO vipPackage = BeanUtils.toBean(createReqVO, VipPackageDO.class);
        vipPackageMapper.insert(vipPackage);
        // 返回
        return vipPackage.getId();
    }

    @Override
    public void updateVipPackage(VipPackageSaveReqVO updateReqVO) {
        // 校验存在
        validateVipPackageExists(updateReqVO.getId());
        // 更新
        VipPackageDO updateObj = BeanUtils.toBean(updateReqVO, VipPackageDO.class);
        vipPackageMapper.updateById(updateObj);
    }

    @Override
    public void deleteVipPackage(Long id) {
        // 校验存在
        validateVipPackageExists(id);
        // 删除
        vipPackageMapper.deleteById(id);
    }

    private void validateVipPackageExists(Long id) {
        if (vipPackageMapper.selectById(id) == null) {
            throw exception(VIP_PACKAGE_NOT_EXISTS);
        }
    }

    @Override
    public VipPackageDO getVipPackage(Long id) {
        return vipPackageMapper.selectById(id);
    }

    @Override
    public PageResult<VipPackageDO> getVipPackagePage(VipPackagePageReqVO pageReqVO) {
        return vipPackageMapper.selectPage(pageReqVO);
    }

}