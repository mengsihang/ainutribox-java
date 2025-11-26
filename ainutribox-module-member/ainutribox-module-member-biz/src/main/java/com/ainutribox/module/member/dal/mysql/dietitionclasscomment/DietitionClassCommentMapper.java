package com.ainutribox.module.member.dal.mysql.dietitionclasscomment;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.dietitionclasscomment.DietitionClassCommentDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.dietitionclasscomment.vo.*;

/**
 * 营养师课程评论 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface DietitionClassCommentMapper extends BaseMapperX<DietitionClassCommentDO> {

    default PageResult<DietitionClassCommentDO> selectPage(DietitionClassCommentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DietitionClassCommentDO>()
                .eqIfPresent(DietitionClassCommentDO::getUserId, reqVO.getUserId())
                .likeIfPresent(DietitionClassCommentDO::getUserNickname, reqVO.getUserNickname())
                .eqIfPresent(DietitionClassCommentDO::getUserAvatar, reqVO.getUserAvatar())
                .likeIfPresent(DietitionClassCommentDO::getDietitionNickname, reqVO.getDietitionNickname())
                .eqIfPresent(DietitionClassCommentDO::getDietitionAvatar, reqVO.getDietitionAvatar())
                .eqIfPresent(DietitionClassCommentDO::getAnonymous, reqVO.getAnonymous())
                .eqIfPresent(DietitionClassCommentDO::getVisible, reqVO.getVisible())
                .eqIfPresent(DietitionClassCommentDO::getScores, reqVO.getScores())
                .eqIfPresent(DietitionClassCommentDO::getDescriptionScores, reqVO.getDescriptionScores())
                .eqIfPresent(DietitionClassCommentDO::getContent, reqVO.getContent())
                .eqIfPresent(DietitionClassCommentDO::getPicUrls, reqVO.getPicUrls())
                .eqIfPresent(DietitionClassCommentDO::getReplyStatus, reqVO.getReplyStatus())
                .eqIfPresent(DietitionClassCommentDO::getDietitionId, reqVO.getDietitionId())
                .eqIfPresent(DietitionClassCommentDO::getReplyContent, reqVO.getReplyContent())
                .betweenIfPresent(DietitionClassCommentDO::getReplyTime, reqVO.getReplyTime())
                .betweenIfPresent(DietitionClassCommentDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DietitionClassCommentDO::getClassId, reqVO.getClassId())
                .orderByDesc(DietitionClassCommentDO::getId));
    }

}