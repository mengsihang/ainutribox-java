package com.ainutribox.module.member.service.spotclass;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.spotclass.vo.*;
import com.ainutribox.module.member.dal.dataobject.spotclass.SpotClassDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 用户点赞表 本表删除使用物理删除 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface SpotClassService {

    /**
     * 创建用户点赞表 本表删除使用物理删除
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSpotClass(@Valid SpotClassSaveReqVO createReqVO);

    /**
     * 更新用户点赞表 本表删除使用物理删除
     *
     * @param updateReqVO 更新信息
     */
    void updateSpotClass(@Valid SpotClassSaveReqVO updateReqVO);

    /**
     * 删除用户点赞表 本表删除使用物理删除
     *
     * @param id 编号
     */
    void deleteSpotClass(Long id);

    /**
     * 获得用户点赞表 本表删除使用物理删除
     *
     * @param id 编号
     * @return 用户点赞表 本表删除使用物理删除
     */
    SpotClassDO getSpotClass(Long id);

    /**
     * 获得用户点赞表 本表删除使用物理删除分页
     *
     * @param pageReqVO 分页查询
     * @return 用户点赞表 本表删除使用物理删除分页
     */
    PageResult<SpotClassDO> getSpotClassPage(SpotClassPageReqVO pageReqVO);

}