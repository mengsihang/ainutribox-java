package com.ainutribox.module.member.service.dietition;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import com.ainutribox.framework.test.core.ut.BaseDbUnitTest;

import com.ainutribox.module.member.controller.admin.dietition.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionInfoDO;
import com.ainutribox.module.member.dal.mysql.dietition.DietitionInfoMapper;
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
 * {@link DietitionInfoServiceImpl} 的单元测试类
 *
 * @author 小泉山网络科技
 */
@Import(DietitionInfoServiceImpl.class)
public class DietitionInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DietitionInfoServiceImpl dietitionInfoService;

    @Resource
    private DietitionInfoMapper dietitionInfoMapper;

    @Test
    public void testCreateDietitionInfo_success() {
        // 准备参数
        DietitionInfoSaveReqVO createReqVO = randomPojo(DietitionInfoSaveReqVO.class).setId(null);

        // 调用
        Long dietitionInfoId = dietitionInfoService.createDietitionInfo(createReqVO);
        // 断言
        assertNotNull(dietitionInfoId);
        // 校验记录的属性是否正确
        DietitionInfoDO dietitionInfo = dietitionInfoMapper.selectById(dietitionInfoId);
        assertPojoEquals(createReqVO, dietitionInfo, "id");
    }

    @Test
    public void testUpdateDietitionInfo_success() {
        // mock 数据
        DietitionInfoDO dbDietitionInfo = randomPojo(DietitionInfoDO.class);
        dietitionInfoMapper.insert(dbDietitionInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DietitionInfoSaveReqVO updateReqVO = randomPojo(DietitionInfoSaveReqVO.class, o -> {
            o.setId(dbDietitionInfo.getId()); // 设置更新的 ID
        });

        // 调用
        dietitionInfoService.updateDietitionInfo(updateReqVO);
        // 校验是否更新正确
        DietitionInfoDO dietitionInfo = dietitionInfoMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, dietitionInfo);
    }

    @Test
    public void testUpdateDietitionInfo_notExists() {
        // 准备参数
        DietitionInfoSaveReqVO updateReqVO = randomPojo(DietitionInfoSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> dietitionInfoService.updateDietitionInfo(updateReqVO), DIETITION_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteDietitionInfo_success() {
        // mock 数据
        DietitionInfoDO dbDietitionInfo = randomPojo(DietitionInfoDO.class);
        dietitionInfoMapper.insert(dbDietitionInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbDietitionInfo.getId();

        // 调用
        dietitionInfoService.deleteDietitionInfo(id);
        // 校验数据不存在了
        assertNull(dietitionInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteDietitionInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> dietitionInfoService.deleteDietitionInfo(id), DIETITION_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDietitionInfoPage() {
        // mock 数据
        DietitionInfoDO dbDietitionInfo = randomPojo(DietitionInfoDO.class, o -> { // 等会查询到
            o.setUserId(null);
            o.setContent(null);
            o.setCreateTime(null);
            o.setScore(null);
            o.setNickName(null);
            o.setExperience(null);
            o.setFavorableRate(null);
            o.setAdviceNum(null);
            o.setCaseNum(null);
            o.setLevel(null);
        });
        dietitionInfoMapper.insert(dbDietitionInfo);
        // 测试 userId 不匹配
        dietitionInfoMapper.insert(cloneIgnoreId(dbDietitionInfo, o -> o.setUserId(null)));
        // 测试 content 不匹配
        dietitionInfoMapper.insert(cloneIgnoreId(dbDietitionInfo, o -> o.setContent(null)));
        // 测试 createTime 不匹配
        dietitionInfoMapper.insert(cloneIgnoreId(dbDietitionInfo, o -> o.setCreateTime(null)));
        // 测试 score 不匹配
        dietitionInfoMapper.insert(cloneIgnoreId(dbDietitionInfo, o -> o.setScore(null)));
        // 测试 nickName 不匹配
        dietitionInfoMapper.insert(cloneIgnoreId(dbDietitionInfo, o -> o.setNickName(null)));
        // 测试 experience 不匹配
        dietitionInfoMapper.insert(cloneIgnoreId(dbDietitionInfo, o -> o.setExperience(null)));
        // 测试 favorableRate 不匹配
        dietitionInfoMapper.insert(cloneIgnoreId(dbDietitionInfo, o -> o.setFavorableRate(null)));
        // 测试 adviceNum 不匹配
        dietitionInfoMapper.insert(cloneIgnoreId(dbDietitionInfo, o -> o.setAdviceNum(null)));
        // 测试 case 不匹配
        dietitionInfoMapper.insert(cloneIgnoreId(dbDietitionInfo, o -> o.setCaseNum(null)));
        // 测试 level 不匹配
        dietitionInfoMapper.insert(cloneIgnoreId(dbDietitionInfo, o -> o.setLevel(null)));
        // 准备参数
        DietitionInfoPageReqVO reqVO = new DietitionInfoPageReqVO();
        reqVO.setUserId(null);
        reqVO.setContent(null);
        reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
        reqVO.setScore(null);
        reqVO.setNickName(null);
        reqVO.setExperience(null);
        reqVO.setFavorableRate(null);
        reqVO.setAdviceNum(null);
        reqVO.setCaseNum(null);
        reqVO.setLevel(null);

        // 调用
        PageResult<DietitionInfoDO> pageResult = dietitionInfoService.getDietitionInfoPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbDietitionInfo, pageResult.getList().get(0));
    }

}