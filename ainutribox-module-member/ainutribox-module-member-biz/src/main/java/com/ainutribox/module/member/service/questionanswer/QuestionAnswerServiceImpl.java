package com.ainutribox.module.member.service.questionanswer;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.module.member.controller.admin.questionanswer.vo.QuestionAnswerPageReqVO;
import com.ainutribox.module.member.controller.admin.questionanswer.vo.QuestionAnswerSaveReqVO;
import com.ainutribox.module.member.dal.dataobject.questionanswer.QuestionAnswerDO;
import com.ainutribox.module.member.dal.mysql.questionanswer.QuestionAnswerMapper;
import com.ainutribox.module.member.util.RedisUtils;
import com.ainutribox.module.trade.dal.redis.RedisKeyConstants;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.QUESTION_ANSWER_NOT_EXISTS;

/**
 * 用户题库答案 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

    @Resource
    private QuestionAnswerMapper questionAnswerMapper;

    @Resource
    private RedisUtils redisUtils;

    @Override
    @CacheEvict(value = RedisKeyConstants.QUESTION, key = "'questionList'")
    public Long createQuestionAnswer(QuestionAnswerSaveReqVO createReqVO) {
        // 插入
        QuestionAnswerDO questionAnswer = BeanUtils.toBean(createReqVO, QuestionAnswerDO.class);
        questionAnswerMapper.insert(questionAnswer);
        // 返回
        return questionAnswer.getId();
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.QUESTION, key = "'questionList'")
    public void updateQuestionAnswer(QuestionAnswerSaveReqVO updateReqVO) {
        // 校验存在
        validateQuestionAnswerExists(updateReqVO.getId());
        // 更新
        QuestionAnswerDO updateObj = BeanUtils.toBean(updateReqVO, QuestionAnswerDO.class);
        questionAnswerMapper.updateById(updateObj);
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.QUESTION, key = "'questionList'")
    public void deleteQuestionAnswer(Long id) {
        // 校验存在
        validateQuestionAnswerExists(id);
        // 删除
        questionAnswerMapper.deleteById(id);
    }

    private void validateQuestionAnswerExists(Long id) {
        if (questionAnswerMapper.selectById(id) == null) {
            throw exception(QUESTION_ANSWER_NOT_EXISTS);
        }
    }

    @Override
    public QuestionAnswerDO getQuestionAnswer(Long id) {
        return questionAnswerMapper.selectById(id);
    }

    @Override
    public PageResult<QuestionAnswerDO> getQuestionAnswerPage(QuestionAnswerPageReqVO pageReqVO) {
        return questionAnswerMapper.selectPage(pageReqVO);
    }

}