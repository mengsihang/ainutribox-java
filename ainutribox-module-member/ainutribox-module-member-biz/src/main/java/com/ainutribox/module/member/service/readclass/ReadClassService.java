package com.ainutribox.module.member.service.readclass;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.readclass.vo.*;
import com.ainutribox.module.member.dal.dataobject.readclass.ReadClassDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 用户阅读记录 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface ReadClassService {

    /**
     * 创建用户阅读记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReadClass(@Valid ReadClassSaveReqVO createReqVO);

    /**
     * 更新用户阅读记录
     *
     * @param updateReqVO 更新信息
     */
    void updateReadClass(@Valid ReadClassSaveReqVO updateReqVO);

    /**
     * 删除用户阅读记录
     *
     * @param id 编号
     */
    void deleteReadClass(Long id);

    /**
     * 获得用户阅读记录
     *
     * @param id 编号
     * @return 用户阅读记录
     */
    ReadClassDO getReadClass(Long id);

    /**
     * 获得用户阅读记录分页
     *
     * @param pageReqVO 分页查询
     * @return 用户阅读记录分页
     */
    PageResult<ReadClassDO> getReadClassPage(ReadClassPageReqVO pageReqVO);

}