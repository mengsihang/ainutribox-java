package com.ainutribox.module.product.service.tags;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import com.ainutribox.framework.test.core.ut.BaseDbUnitTest;

import com.ainutribox.module.product.controller.admin.tags.vo.*;
import com.ainutribox.module.product.dal.dataobject.tags.TagsDO;
import com.ainutribox.module.product.dal.mysql.tags.TagsMapper;
import com.ainutribox.framework.common.pojo.PageResult;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static com.ainutribox.module.product.enums.ErrorCodeConstants.*;
import static com.ainutribox.framework.test.core.util.AssertUtils.*;
import static com.ainutribox.framework.test.core.util.RandomUtils.*;
import static com.ainutribox.framework.common.util.date.LocalDateTimeUtils.*;
import static com.ainutribox.framework.common.util.object.ObjectUtils.*;
import static com.ainutribox.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link TagsServiceImpl} 的单元测试类
 *
 * @author 小泉山网络科技
 */
@Import(TagsServiceImpl.class)
public class TagsServiceImplTest extends BaseDbUnitTest {

    @Resource
    private TagsServiceImpl tagsService;

    @Resource
    private TagsMapper tagsMapper;

    @Test
    public void testCreateTags_success() {
        // 准备参数
        TagsSaveReqVO createReqVO = randomPojo(TagsSaveReqVO.class).setId(null);

        // 调用
        Long tagsId = tagsService.createTags(createReqVO);
        // 断言
        assertNotNull(tagsId);
        // 校验记录的属性是否正确
        TagsDO tags = tagsMapper.selectById(tagsId);
        assertPojoEquals(createReqVO, tags, "id");
    }

    @Test
    public void testUpdateTags_success() {
        // mock 数据
        TagsDO dbTags = randomPojo(TagsDO.class);
        tagsMapper.insert(dbTags);// @Sql: 先插入出一条存在的数据
        // 准备参数
        TagsSaveReqVO updateReqVO = randomPojo(TagsSaveReqVO.class, o -> {
            o.setId(dbTags.getId()); // 设置更新的 ID
        });

        // 调用
        tagsService.updateTags(updateReqVO);
        // 校验是否更新正确
        TagsDO tags = tagsMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, tags);
    }

    @Test
    public void testUpdateTags_notExists() {
        // 准备参数
        TagsSaveReqVO updateReqVO = randomPojo(TagsSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> tagsService.updateTags(updateReqVO), TAGS_NOT_EXISTS);
    }

    @Test
    public void testDeleteTags_success() {
        // mock 数据
        TagsDO dbTags = randomPojo(TagsDO.class);
        tagsMapper.insert(dbTags);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbTags.getId();

        // 调用
        tagsService.deleteTags(id);
       // 校验数据不存在了
       assertNull(tagsMapper.selectById(id));
    }

    @Test
    public void testDeleteTags_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> tagsService.deleteTags(id), TAGS_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetTagsPage() {
       // mock 数据
       TagsDO dbTags = randomPojo(TagsDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setColor(null);
           o.setSort(null);
           o.setDescription(null);
           o.setStatus(null);
           o.setCreateTime(null);
       });
       tagsMapper.insert(dbTags);
       // 测试 name 不匹配
       tagsMapper.insert(cloneIgnoreId(dbTags, o -> o.setName(null)));
       // 测试 color 不匹配
       tagsMapper.insert(cloneIgnoreId(dbTags, o -> o.setColor(null)));
       // 测试 sort 不匹配
       tagsMapper.insert(cloneIgnoreId(dbTags, o -> o.setSort(null)));
       // 测试 description 不匹配
       tagsMapper.insert(cloneIgnoreId(dbTags, o -> o.setDescription(null)));
       // 测试 status 不匹配
       tagsMapper.insert(cloneIgnoreId(dbTags, o -> o.setStatus(null)));
       // 测试 createTime 不匹配
       tagsMapper.insert(cloneIgnoreId(dbTags, o -> o.setCreateTime(null)));
       // 准备参数
       TagsPageReqVO reqVO = new TagsPageReqVO();
       reqVO.setName(null);
       reqVO.setColor(null);
       reqVO.setSort(null);
       reqVO.setDescription(null);
       reqVO.setStatus(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<TagsDO> pageResult = tagsService.getTagsPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbTags, pageResult.getList().get(0));
    }

}