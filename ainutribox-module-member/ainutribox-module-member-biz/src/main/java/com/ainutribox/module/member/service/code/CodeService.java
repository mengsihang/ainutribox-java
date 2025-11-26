package com.ainutribox.module.member.service.code;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.code.vo.*;
import com.ainutribox.module.member.dal.dataobject.code.CodeDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 积分兑换码 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface CodeService {

    /**
     * 创建积分兑换码
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCode(@Valid CodeSaveReqVO createReqVO);

    /**
     * 更新积分兑换码
     *
     * @param updateReqVO 更新信息
     */
    void updateCode(@Valid CodeSaveReqVO updateReqVO);

    /**
     * 删除积分兑换码
     *
     * @param id 编号
     */
    void deleteCode(Long id);

    /**
     * 获得积分兑换码
     *
     * @param id 编号
     * @return 积分兑换码
     */
    CodeDO getCode(Long id);

    /**
     * 获得积分兑换码分页
     *
     * @param pageReqVO 分页查询
     * @return 积分兑换码分页
     */
    PageResult<CodeDO> getCodePage(CodePageReqVO pageReqVO);



}