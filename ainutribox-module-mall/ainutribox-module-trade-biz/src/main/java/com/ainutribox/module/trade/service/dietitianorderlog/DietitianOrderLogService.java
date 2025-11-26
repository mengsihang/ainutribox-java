package com.ainutribox.module.trade.service.dietitianorderlog;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.trade.controller.admin.dietitianorderlog.vo.*;
import com.ainutribox.module.trade.dal.dataobject.dietitianorderlog.DietitianOrderLogDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 营养师自组营养包售卖订单日志 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface DietitianOrderLogService {

    /**
     * 创建营养师自组营养包售卖订单日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDietitianOrderLog(@Valid DietitianOrderLogSaveReqVO createReqVO);

    /**
     * 更新营养师自组营养包售卖订单日志
     *
     * @param updateReqVO 更新信息
     */
    void updateDietitianOrderLog(@Valid DietitianOrderLogSaveReqVO updateReqVO);

    /**
     * 删除营养师自组营养包售卖订单日志
     *
     * @param id 编号
     */
    void deleteDietitianOrderLog(Long id);

    /**
     * 获得营养师自组营养包售卖订单日志
     *
     * @param id 编号
     * @return 营养师自组营养包售卖订单日志
     */
    DietitianOrderLogDO getDietitianOrderLog(Long id);

    /**
     * 获得营养师自组营养包售卖订单日志分页
     *
     * @param pageReqVO 分页查询
     * @return 营养师自组营养包售卖订单日志分页
     */
    PageResult<DietitianOrderLogDO> getDietitianOrderLogPage(DietitianOrderLogPageReqVO pageReqVO);

}