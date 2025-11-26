package com.ainutribox.module.member.service.readclass;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.member.controller.admin.readclass.vo.*;
import com.ainutribox.module.member.dal.dataobject.readclass.ReadClassDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.member.dal.mysql.readclass.ReadClassMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 用户阅读记录 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class ReadClassServiceImpl implements ReadClassService {

    @Resource
    private ReadClassMapper readClassMapper;

    @Override
    public Long createReadClass(ReadClassSaveReqVO createReqVO) {
        // 插入
        ReadClassDO readClass = BeanUtils.toBean(createReqVO, ReadClassDO.class);
        readClassMapper.insert(readClass);
        // 返回
        return readClass.getId();
    }

    @Override
    public void updateReadClass(ReadClassSaveReqVO updateReqVO) {
        // 校验存在
        validateReadClassExists(updateReqVO.getId());
        // 更新
        ReadClassDO updateObj = BeanUtils.toBean(updateReqVO, ReadClassDO.class);
        readClassMapper.updateById(updateObj);
    }

    @Override
    public void deleteReadClass(Long id) {
        // 校验存在
        validateReadClassExists(id);
        // 删除
        readClassMapper.deleteById(id);
    }

    private void validateReadClassExists(Long id) {
        if (readClassMapper.selectById(id) == null) {
            throw exception(READ_CLASS_NOT_EXISTS);
        }
    }

    @Override
    public ReadClassDO getReadClass(Long id) {
        return readClassMapper.selectById(id);
    }

    @Override
    public PageResult<ReadClassDO> getReadClassPage(ReadClassPageReqVO pageReqVO) {
        return readClassMapper.selectPage(pageReqVO);
    }

}