package com.ainutribox.module.member.service.question;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.module.member.controller.admin.question.vo.QuestionPageReqVO;
import com.ainutribox.module.member.controller.admin.question.vo.QuestionSaveReqVO;
import com.ainutribox.module.member.dal.dataobject.question.QuestionDO;
import com.ainutribox.module.member.dal.mysql.question.QuestionMapper;
import com.ainutribox.module.member.util.RedisUtils;
import com.ainutribox.module.trade.dal.redis.RedisKeyConstants;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.QUESTION_NOT_EXISTS;

/**
 * 用户题库 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private RedisUtils redisUtils;

    @Override
    @CacheEvict(value = RedisKeyConstants.QUESTION, key = "'questionList'")
    public Long createQuestion(QuestionSaveReqVO createReqVO) {
        // 插入
        QuestionDO question = BeanUtils.toBean(createReqVO, QuestionDO.class);
        questionMapper.insert(question);
        // 返回
        return question.getId();
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.QUESTION, key = "'questionList'")
    public void updateQuestion(QuestionSaveReqVO updateReqVO) {
        // 校验存在
        validateQuestionExists(updateReqVO.getId());
        // 更新
        QuestionDO updateObj = BeanUtils.toBean(updateReqVO, QuestionDO.class);
        questionMapper.updateById(updateObj);
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.QUESTION, key = "'questionList'")
    public void deleteQuestion(Long id) {
        // 校验存在
        validateQuestionExists(id);
        // 删除
        questionMapper.deleteById(id);
    }

    private void validateQuestionExists(Long id) {
        if (questionMapper.selectById(id) == null) {
            throw exception(QUESTION_NOT_EXISTS);
        }
    }

    @Override
    public QuestionDO getQuestion(Long id) {
        return questionMapper.selectById(id);
    }

    @Override
    public PageResult<QuestionDO> getQuestionPage(QuestionPageReqVO pageReqVO) {
        return questionMapper.selectPage(pageReqVO);
    }

}