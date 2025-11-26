package com.ainutribox.module.member.service.dietitionclasscomment;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.member.controller.admin.dietitionclasscomment.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietitionclasscomment.DietitionClassCommentDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.member.dal.mysql.dietitionclasscomment.DietitionClassCommentMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 营养师课程评论 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class DietitionClassCommentServiceImpl implements DietitionClassCommentService {

    @Resource
    private DietitionClassCommentMapper dietitionClassCommentMapper;

    @Override
    public Long createDietitionClassComment(DietitionClassCommentSaveReqVO createReqVO) {
        // 插入
        DietitionClassCommentDO dietitionClassComment = BeanUtils.toBean(createReqVO, DietitionClassCommentDO.class);
        dietitionClassCommentMapper.insert(dietitionClassComment);
        // 返回
        return dietitionClassComment.getId();
    }

    @Override
    public void updateDietitionClassComment(DietitionClassCommentSaveReqVO updateReqVO) {
        // 校验存在
        validateDietitionClassCommentExists(updateReqVO.getId());
        // 更新
        DietitionClassCommentDO updateObj = BeanUtils.toBean(updateReqVO, DietitionClassCommentDO.class);
        dietitionClassCommentMapper.updateById(updateObj);
    }

    @Override
    public void deleteDietitionClassComment(Long id) {
        // 校验存在
        validateDietitionClassCommentExists(id);
        // 删除
        dietitionClassCommentMapper.deleteById(id);
    }

    private void validateDietitionClassCommentExists(Long id) {
        if (dietitionClassCommentMapper.selectById(id) == null) {
            throw exception(DIETITION_CLASS_COMMENT_NOT_EXISTS);
        }
    }

    @Override
    public DietitionClassCommentDO getDietitionClassComment(Long id) {
        return dietitionClassCommentMapper.selectById(id);
    }

    @Override
    public PageResult<DietitionClassCommentDO> getDietitionClassCommentPage(DietitionClassCommentPageReqVO pageReqVO) {
        return dietitionClassCommentMapper.selectPage(pageReqVO);
    }

}