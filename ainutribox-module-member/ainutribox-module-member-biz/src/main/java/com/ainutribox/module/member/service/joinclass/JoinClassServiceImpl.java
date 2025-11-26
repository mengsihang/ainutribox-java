package com.ainutribox.module.member.service.joinclass;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.member.controller.admin.joinclass.vo.*;
import com.ainutribox.module.member.dal.dataobject.joinclass.JoinClassDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.member.dal.mysql.joinclass.JoinClassMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 用户课程 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class JoinClassServiceImpl implements JoinClassService {

    @Resource
    private JoinClassMapper joinClassMapper;

    @Override
    public Long createJoinClass(JoinClassSaveReqVO createReqVO) {
        // 插入
        JoinClassDO joinClass = BeanUtils.toBean(createReqVO, JoinClassDO.class);
        joinClassMapper.insert(joinClass);
        // 返回
        return joinClass.getId();
    }

    @Override
    public void updateJoinClass(JoinClassSaveReqVO updateReqVO) {
        // 校验存在
        validateJoinClassExists(updateReqVO.getId());
        // 更新
        JoinClassDO updateObj = BeanUtils.toBean(updateReqVO, JoinClassDO.class);
        joinClassMapper.updateById(updateObj);
    }

    @Override
    public void deleteJoinClass(Long id) {
        // 校验存在
        validateJoinClassExists(id);
        // 删除
        joinClassMapper.deleteById(id);
    }

    private void validateJoinClassExists(Long id) {
        if (joinClassMapper.selectById(id) == null) {
            throw exception(JOIN_CLASS_NOT_EXISTS);
        }
    }

    @Override
    public JoinClassDO getJoinClass(Long id) {
        return joinClassMapper.selectById(id);
    }

    @Override
    public PageResult<JoinClassDO> getJoinClassPage(JoinClassPageReqVO pageReqVO) {
        return joinClassMapper.selectPage(pageReqVO);
    }

}