package com.ainutribox.module.member.service.vip;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.member.controller.admin.vip.vo.*;
import com.ainutribox.module.member.dal.dataobject.vip.MemberVipDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.member.dal.mysql.vip.MemberVipMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 用户vip Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class MemberVipServiceImpl implements MemberVipService {

    @Resource
    private MemberVipMapper vipMapper;

    @Override
    public Long createVip(MemberVipSaveReqVO createReqVO) {
        // 插入
        MemberVipDO vip = BeanUtils.toBean(createReqVO, MemberVipDO.class);
        vipMapper.insert(vip);
        // 返回
        return vip.getId();
    }

    @Override
    public void updateVip(MemberVipSaveReqVO updateReqVO) {
        // 校验存在
        validateVipExists(updateReqVO.getId());
        // 更新
        MemberVipDO updateObj = BeanUtils.toBean(updateReqVO, MemberVipDO.class);
        vipMapper.updateById(updateObj);
    }

    @Override
    public void deleteVip(Long id) {
        // 校验存在
        validateVipExists(id);
        // 删除
        vipMapper.deleteById(id);
    }

    private void validateVipExists(Long id) {
        if (vipMapper.selectById(id) == null) {
            throw exception(VIP_NOT_EXISTS);
        }
    }

    @Override
    public MemberVipDO getVip(Long id) {
        return vipMapper.selectById(id);
    }

    @Override
    public PageResult<MemberVipDO> getVipPage(MemberVipPageReqVO pageReqVO) {
        return vipMapper.selectPage(pageReqVO);
    }

}