package com.ainutribox.module.member.service.question;

import cn.hutool.core.collection.CollUtil;
import com.ainutribox.framework.common.enums.CommonStatusEnum;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.module.member.controller.app.question.vo.AppQuestionRespVo;
import com.ainutribox.module.member.dal.dataobject.question.QuestionDO;
import com.ainutribox.module.member.dal.dataobject.questionanswer.QuestionAnswerDO;
import com.ainutribox.module.member.dal.mysql.question.QuestionMapper;
import com.ainutribox.module.member.dal.mysql.questionanswer.QuestionAnswerMapper;
import com.ainutribox.module.member.staticdata.QuestionStaticData;
import com.ainutribox.module.trade.dal.redis.RedisKeyConstants;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * App 用户题库 Service 接口
 *
 * @author lucifer
 */
@Service
public class AppQuestionServiceImpl implements AppQuestionService {

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private QuestionAnswerMapper questionAnswerMapper;

//    @Override
//    public List<AppQuestionRespVo> getQuestion() {
//
//        List<QuestionDO> questionDOList = questionMapper.selectList(new LambdaQueryWrapperX<QuestionDO>()
//                .eq(QuestionDO::getStatus, CommonStatusEnum.ENABLE.getStatus())
//                .eq(QuestionDO::getDeleted, CommonStatusEnum.ENABLE.getStatus()));
//
//        if (CollUtil.isEmpty(questionDOList)) {
//            return null;
//        }
//        Map<Integer, List<QuestionDO>> groupedMap = questionDOList.stream()
//                .collect(Collectors.groupingBy(
//                        QuestionDO::getTypeNum,
//                        Collectors.collectingAndThen(
//                                Collectors.toList(),
//                                list -> list.stream()
//                                        .sorted(Comparator.comparing(QuestionDO::getSort))
//                                        .collect(Collectors.toList())
//                        )
//                ));
//
//        List<AppQuestionRespVo> result = new ArrayList<>();
//        for (Map.Entry<Integer, List<QuestionDO>> entry : groupedMap.entrySet()) {
//            AppQuestionRespVo questionRespVo = new AppQuestionRespVo();
//            questionRespVo.setTypeNum(entry.getKey());
//            questionRespVo.setQuestionList(entry.getValue());
//            questionRespVo.setTypeName(QuestionStaticData.getDataMap().get(entry.getKey()));
//
//
//            result.add(questionRespVo);
//        }
//
//        return result;
//
//    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.QUESTION,key = "'questionList'")
    public List<AppQuestionRespVo> getQuestion() {

        List<QuestionDO> questionDOList = questionMapper.selectList(new LambdaQueryWrapperX<QuestionDO>()
                .eq(QuestionDO::getStatus, CommonStatusEnum.ENABLE.getStatus())
                .eq(QuestionDO::getDeleted, CommonStatusEnum.ENABLE.getStatus())
                .orderByAsc(QuestionDO::getSort));
        List<AppQuestionRespVo> resultList = BeanUtils.toBean(questionDOList, AppQuestionRespVo.class);
        for (AppQuestionRespVo questionDO : resultList) {

            questionDO.setAnswerList(questionAnswerMapper.selectList(new LambdaQueryWrapperX<QuestionAnswerDO>()
                   .eq(QuestionAnswerDO::getQuestionId, questionDO.getId())
                   .eq(QuestionAnswerDO::getStatus, CommonStatusEnum.ENABLE.getStatus())
                   .eq(QuestionAnswerDO::getDeleted, CommonStatusEnum.ENABLE.getStatus())));
        }
        return  resultList;
    }
}
