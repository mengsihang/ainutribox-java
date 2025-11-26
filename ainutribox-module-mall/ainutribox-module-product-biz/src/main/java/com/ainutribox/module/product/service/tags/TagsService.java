package com.ainutribox.module.product.service.tags;

import java.util.*;

import com.ainutribox.module.product.controller.admin.brand.vo.ProductBrandListReqVO;
import com.ainutribox.module.product.dal.dataobject.brand.ProductBrandDO;
import jakarta.validation.*;
import com.ainutribox.module.product.controller.admin.tags.vo.*;
import com.ainutribox.module.product.dal.dataobject.tags.TagsDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 商品标签 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface TagsService {

    /**
     * 创建商品标签
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTags(@Valid TagsSaveReqVO createReqVO);

    /**
     * 更新商品标签
     *
     * @param updateReqVO 更新信息
     */
    void updateTags(@Valid TagsSaveReqVO updateReqVO);

    /**
     * 删除商品标签
     *
     * @param id 编号
     */
    void deleteTags(Long id);

    /**
     * 获得商品标签
     *
     * @param id 编号
     * @return 商品标签
     */
    TagsDO getTags(Long id);

    /**
     * 获取指定状态的标签列表
     *
     * @param status 状态
     * @return  返回标签列表
     */
    List<TagsDO> getTagsListByStatus(Integer status);

    /**
     * 获得商品标签分页
     *
     * @param pageReqVO 分页查询
     * @return 商品标签分页
     */
    PageResult<TagsDO> getTagsPage(TagsPageReqVO pageReqVO);

}