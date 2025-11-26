package com.ainutribox.module.member.service.question;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import com.ainutribox.framework.test.core.ut.BaseDbUnitTest;

import com.ainutribox.module.member.controller.admin.question.vo.*;
import com.ainutribox.module.member.dal.dataobject.question.QuestionDO;
import com.ainutribox.module.member.dal.mysql.question.QuestionMapper;
import com.ainutribox.framework.common.pojo.PageResult;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;
import static com.ainutribox.framework.test.core.util.AssertUtils.*;
import static com.ainutribox.framework.test.core.util.RandomUtils.*;
import static com.ainutribox.framework.common.util.date.LocalDateTimeUtils.*;
import static com.ainutribox.framework.common.util.object.ObjectUtils.*;
import static com.ainutribox.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link QuestionServiceImpl} 的单元测试类
 *
 * @author 小泉山网络科技
 */
@Import(QuestionServiceImpl.class)
public class QuestionServiceImplTest extends BaseDbUnitTest {

    @Resource
    private QuestionServiceImpl questionService;

    @Resource
    private QuestionMapper questionMapper;

    @Test
    public void testCreateQuestion_success() {
        // 准备参数
        QuestionSaveReqVO createReqVO = randomPojo(QuestionSaveReqVO.class).setId(null);

        // 调用
        Long questionId = questionService.createQuestion(createReqVO);
        // 断言
        assertNotNull(questionId);
        // 校验记录的属性是否正确
        QuestionDO question = questionMapper.selectById(questionId);
        assertPojoEquals(createReqVO, question, "id");
    }

    @Test
    public void testUpdateQuestion_success() {
        // mock 数据
        QuestionDO dbQuestion = randomPojo(QuestionDO.class);
        questionMapper.insert(dbQuestion);// @Sql: 先插入出一条存在的数据
        // 准备参数
        QuestionSaveReqVO updateReqVO = randomPojo(QuestionSaveReqVO.class, o -> {
            o.setId(dbQuestion.getId()); // 设置更新的 ID
        });

        // 调用
        questionService.updateQuestion(updateReqVO);
        // 校验是否更新正确
        QuestionDO question = questionMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, question);
    }

    @Test
    public void testUpdateQuestion_notExists() {
        // 准备参数
        QuestionSaveReqVO updateReqVO = randomPojo(QuestionSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> questionService.updateQuestion(updateReqVO), QUESTION_NOT_EXISTS);
    }

    @Test
    public void testDeleteQuestion_success() {
        // mock 数据
        QuestionDO dbQuestion = randomPojo(QuestionDO.class);
        questionMapper.insert(dbQuestion);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbQuestion.getId();

        // 调用
        questionService.deleteQuestion(id);
        // 校验数据不存在了
        assertNull(questionMapper.selectById(id));
    }

    @Test
    public void testDeleteQuestion_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> questionService.deleteQuestion(id), QUESTION_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetQuestionPage() {
        // mock 数据
        QuestionDO dbQuestion = randomPojo(QuestionDO.class, o -> { // 等会查询到
            o.setTypeNum(null);
            o.setTitle(null);
            o.setPicUrl(null);
            o.setAnswer(null);
            o.setSort(null);
            o.setStatus(null);
            o.setCreateTime(null);
        });
        questionMapper.insert(dbQuestion);
        // 测试 typeNum 不匹配
        questionMapper.insert(cloneIgnoreId(dbQuestion, o -> o.setTypeNum(null)));
        // 测试 title 不匹配
        questionMapper.insert(cloneIgnoreId(dbQuestion, o -> o.setTitle(null)));
        // 测试 picUrl 不匹配
        questionMapper.insert(cloneIgnoreId(dbQuestion, o -> o.setPicUrl(null)));
        // 测试 answer 不匹配
        questionMapper.insert(cloneIgnoreId(dbQuestion, o -> o.setAnswer(null)));
        // 测试 sort 不匹配
        questionMapper.insert(cloneIgnoreId(dbQuestion, o -> o.setSort(null)));
        // 测试 status 不匹配
        questionMapper.insert(cloneIgnoreId(dbQuestion, o -> o.setStatus(null)));
        // 测试 createTime 不匹配
        questionMapper.insert(cloneIgnoreId(dbQuestion, o -> o.setCreateTime(null)));
        // 准备参数
        QuestionPageReqVO reqVO = new QuestionPageReqVO();
        reqVO.setTypeNum(null);
        reqVO.setTitle(null);
        reqVO.setPicUrl(null);
        reqVO.setAnswer(null);
        reqVO.setSort(null);
        reqVO.setStatus(null);
        reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

        // 调用
        PageResult<QuestionDO> pageResult = questionService.getQuestionPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbQuestion, pageResult.getList().get(0));
    }

}