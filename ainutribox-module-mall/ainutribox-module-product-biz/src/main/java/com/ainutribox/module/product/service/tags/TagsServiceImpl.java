package com.ainutribox.module.product.service.tags;

import com.ainutribox.module.product.dal.dataobject.brand.ProductBrandDO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.product.controller.admin.tags.vo.*;
import com.ainutribox.module.product.dal.dataobject.tags.TagsDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.product.dal.mysql.tags.TagsMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.product.enums.ErrorCodeConstants.*;

/**
 * 商品标签 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class TagsServiceImpl implements TagsService {

    @Resource
    private TagsMapper tagsMapper;

    @Override
    public Long createTags(TagsSaveReqVO createReqVO) {
        // 插入
        TagsDO tags = BeanUtils.toBean(createReqVO, TagsDO.class);
        tagsMapper.insert(tags);
        // 返回
        return tags.getId();
    }

    @Override
    public void updateTags(TagsSaveReqVO updateReqVO) {
        // 校验存在
        validateTagsExists(updateReqVO.getId());
        // 更新
        TagsDO updateObj = BeanUtils.toBean(updateReqVO, TagsDO.class);
        tagsMapper.updateById(updateObj);
    }

    @Override
    public void deleteTags(Long id) {
        // 校验存在
        validateTagsExists(id);
        // 删除
        tagsMapper.deleteById(id);
    }

    private void validateTagsExists(Long id) {
        if (tagsMapper.selectById(id) == null) {
            throw exception(TAGS_NOT_EXISTS);
        }
    }

    @Override
    public TagsDO getTags(Long id) {
        return tagsMapper.selectById(id);
    }


    @Override
    public PageResult<TagsDO> getTagsPage(TagsPageReqVO pageReqVO) {
        return tagsMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TagsDO> getTagsListByStatus(Integer status) {
        return tagsMapper.selectListByStatus(status);
    }

}