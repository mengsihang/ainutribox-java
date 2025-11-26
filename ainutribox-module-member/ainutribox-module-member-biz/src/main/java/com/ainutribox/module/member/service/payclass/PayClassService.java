package com.ainutribox.module.member.service.payclass;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.payclass.vo.*;
import com.ainutribox.module.member.dal.dataobject.payclass.PayClassDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 用户购买课程 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface PayClassService {

    /**
     * 创建用户购买课程
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPayClass(@Valid PayClassSaveReqVO createReqVO);

    /**
     * 更新用户购买课程
     *
     * @param updateReqVO 更新信息
     */
    void updatePayClass(@Valid PayClassSaveReqVO updateReqVO);

    /**
     * 删除用户购买课程
     *
     * @param id 编号
     */
    void deletePayClass(Long id);

    /**
     * 获得用户购买课程
     *
     * @param id 编号
     * @return 用户购买课程
     */
    PayClassDO getPayClass(Long id);

    /**
     * 获得用户购买课程分页
     *
     * @param pageReqVO 分页查询
     * @return 用户购买课程分页
     */
    PageResult<PayClassDO> getPayClassPage(PayClassPageReqVO pageReqVO);

}