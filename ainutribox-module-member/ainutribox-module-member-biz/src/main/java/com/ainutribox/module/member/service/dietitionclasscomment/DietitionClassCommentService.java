package com.ainutribox.module.member.service.dietitionclasscomment;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.dietitionclasscomment.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietitionclasscomment.DietitionClassCommentDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 营养师课程评论 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface DietitionClassCommentService {

    /**
     * 创建营养师课程评论
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDietitionClassComment(@Valid DietitionClassCommentSaveReqVO createReqVO);

    /**
     * 更新营养师课程评论
     *
     * @param updateReqVO 更新信息
     */
    void updateDietitionClassComment(@Valid DietitionClassCommentSaveReqVO updateReqVO);

    /**
     * 删除营养师课程评论
     *
     * @param id 编号
     */
    void deleteDietitionClassComment(Long id);

    /**
     * 获得营养师课程评论
     *
     * @param id 编号
     * @return 营养师课程评论
     */
    DietitionClassCommentDO getDietitionClassComment(Long id);

    /**
     * 获得营养师课程评论分页
     *
     * @param pageReqVO 分页查询
     * @return 营养师课程评论分页
     */
    PageResult<DietitionClassCommentDO> getDietitionClassCommentPage(DietitionClassCommentPageReqVO pageReqVO);




}