package com.ainutribox.module.member.dal.mysql.joinclass;

import java.time.LocalDateTime;
import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.joinclass.JoinClassDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.joinclass.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 用户课程 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface JoinClassMapper extends BaseMapperX<JoinClassDO> {

    default PageResult<JoinClassDO> selectPage(JoinClassPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<JoinClassDO>()
                .eqIfPresent(JoinClassDO::getMemberId, reqVO.getMemberId())
                .eqIfPresent(JoinClassDO::getClassId, reqVO.getClassId())
                .betweenIfPresent(JoinClassDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(JoinClassDO::getId));
    }


    /**
     * 物理删除指定用户指定课程
     * @param memberId
     * @param classId
     * @return
     */
    @Delete("DELETE FROM member_join_class WHERE member_id = #{memberId} and  class_id = #{classId}")
    Integer deleteByUserIdAndClassId(@Param("memberId") Long memberId, @Param("classId")Long classId);

}