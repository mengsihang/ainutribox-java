package com.ainutribox.module.member.service.dietition;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import com.ainutribox.framework.test.core.ut.BaseDbUnitTest;

import com.ainutribox.module.member.controller.admin.dietition.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionDO;
import com.ainutribox.module.member.dal.mysql.dietition.DietitionMapper;
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
 * {@link DietitionServiceImpl} 的单元测试类
 *
 * @author 小泉山网络科技
 */
@Import(DietitionServiceImpl.class)
public class DietitionServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DietitionServiceImpl dietitionService;

    @Resource
    private DietitionMapper dietitionMapper;

    @Test
    public void testCreateDietition_success() {
        // 准备参数
        DietitionSaveReqVO createReqVO = randomPojo(DietitionSaveReqVO.class).setId(null);

        // 调用
        Long dietitionId = dietitionService.createDietition(createReqVO);
        // 断言
        assertNotNull(dietitionId);
        // 校验记录的属性是否正确
        DietitionDO dietition = dietitionMapper.selectById(dietitionId);
        assertPojoEquals(createReqVO, dietition, "id");
    }

    @Test
    public void testUpdateDietition_success() {
        // mock 数据
        DietitionDO dbDietition = randomPojo(DietitionDO.class);
        dietitionMapper.insert(dbDietition);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DietitionSaveReqVO updateReqVO = randomPojo(DietitionSaveReqVO.class, o -> {
            o.setId(dbDietition.getId()); // 设置更新的 ID
        });

        // 调用
        dietitionService.updateDietition(updateReqVO);
        // 校验是否更新正确
        DietitionDO dietition = dietitionMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, dietition);
    }

    @Test
    public void testUpdateDietition_notExists() {
        // 准备参数
        DietitionSaveReqVO updateReqVO = randomPojo(DietitionSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> dietitionService.updateDietition(updateReqVO), DIETITION_NOT_EXISTS);
    }

    @Test
    public void testDeleteDietition_success() {
        // mock 数据
        DietitionDO dbDietition = randomPojo(DietitionDO.class);
        dietitionMapper.insert(dbDietition);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbDietition.getId();

        // 调用
        dietitionService.deleteDietition(id);
        // 校验数据不存在了
        assertNull(dietitionMapper.selectById(id));
    }

    @Test
    public void testDeleteDietition_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> dietitionService.deleteDietition(id), DIETITION_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDietitionPage() {
        // mock 数据
        DietitionDO dbDietition = randomPojo(DietitionDO.class, o -> { // 等会查询到
            o.setUserId(null);
            o.setContent(null);
            o.setCreateTime(null);
            o.setStatus(null);
            o.setTel(null);
            o.setIdPicFront(null);
            o.setIdPicBack(null);
            o.setCertificatePic(null);
        });
        dietitionMapper.insert(dbDietition);
        // 测试 userId 不匹配
        dietitionMapper.insert(cloneIgnoreId(dbDietition, o -> o.setUserId(null)));
        // 测试 content 不匹配
        dietitionMapper.insert(cloneIgnoreId(dbDietition, o -> o.setContent(null)));
        // 测试 createTime 不匹配
        dietitionMapper.insert(cloneIgnoreId(dbDietition, o -> o.setCreateTime(null)));
        // 测试 status 不匹配
        dietitionMapper.insert(cloneIgnoreId(dbDietition, o -> o.setStatus(null)));
        // 测试 tel 不匹配
        dietitionMapper.insert(cloneIgnoreId(dbDietition, o -> o.setTel(null)));
        // 测试 idPicFront 不匹配
        dietitionMapper.insert(cloneIgnoreId(dbDietition, o -> o.setIdPicFront(null)));
        // 测试 idPicBack 不匹配
        dietitionMapper.insert(cloneIgnoreId(dbDietition, o -> o.setIdPicBack(null)));
        // 测试 certificatePic 不匹配
        dietitionMapper.insert(cloneIgnoreId(dbDietition, o -> o.setCertificatePic(null)));
        // 准备参数
        DietitionPageReqVO reqVO = new DietitionPageReqVO();
        reqVO.setUserId(null);
        reqVO.setContent(null);
        reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
        reqVO.setStatus(null);
        reqVO.setTel(null);
        reqVO.setIdPicFront(null);
        reqVO.setIdPicBack(null);
        reqVO.setCertificatePic(null);

        // 调用
        PageResult<DietitionDO> pageResult = dietitionService.getDietitionPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbDietition, pageResult.getList().get(0));
    }

}