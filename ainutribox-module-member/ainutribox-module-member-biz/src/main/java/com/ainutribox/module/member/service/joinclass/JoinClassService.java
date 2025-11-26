package com.ainutribox.module.member.service.joinclass;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.joinclass.vo.*;
import com.ainutribox.module.member.dal.dataobject.joinclass.JoinClassDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 用户课程 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface JoinClassService {

    /**
     * 创建用户课程
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createJoinClass(@Valid JoinClassSaveReqVO createReqVO);

    /**
     * 更新用户课程
     *
     * @param updateReqVO 更新信息
     */
    void updateJoinClass(@Valid JoinClassSaveReqVO updateReqVO);

    /**
     * 删除用户课程
     *
     * @param id 编号
     */
    void deleteJoinClass(Long id);

    /**
     * 获得用户课程
     *
     * @param id 编号
     * @return 用户课程
     */
    JoinClassDO getJoinClass(Long id);

    /**
     * 获得用户课程分页
     *
     * @param pageReqVO 分页查询
     * @return 用户课程分页
     */
    PageResult<JoinClassDO> getJoinClassPage(JoinClassPageReqVO pageReqVO);

}