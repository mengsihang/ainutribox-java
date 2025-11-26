package com.ainutribox.module.member.service.dietitionclass;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.module.member.controller.admin.dietition.vo.DietitionInfoRespVO;
import com.ainutribox.module.member.controller.app.dietitionclass.dto.*;
import com.ainutribox.module.member.controller.app.dietitionclass.vo.AppClassChildVO;
import com.ainutribox.module.member.controller.app.dietitionclass.vo.AppDietitionInfoVo;
import com.ainutribox.module.member.controller.app.dietitionclass.vo.AppUserClassVO;
import com.ainutribox.module.member.dal.dataobject.dietitionclasschild.DietitionClassChildDO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户视角下课程相关
 *
 * @author lucifer
 * @date 2024-06-29 11:37
 */
public interface AppUserClassService {

    /**
     * 获取课程分页
     * @param appUserClassDTO
     * @return
     */
     PageResult<AppUserClassVO> getAllClassPage(AppUserClassDTO appUserClassDTO);

    /**
     * 加入课程
     * @param userId
     * @param joinOrCancelClassDTO
     */
     void joinClass(Long userId, AppJoinOrCancelClassDTO joinOrCancelClassDTO);

    /**
     * 移除课程
     * @param userId
     * @param joinOrCancelClassDTO
     */
     void cancelClass(Long userId, AppJoinOrCancelClassDTO joinOrCancelClassDTO);

    /**
     * 获取用户加入的课程
     * @param userId
     * @return
     */
    List<AppUserClassVO> selectUserJoinClassList(Long userId);

    /**
     * 获取用户购买的课程
     * @param userId
     * @return
     */
    List<AppUserClassVO> selectUserBuyClassList(Long userId);

    /**
     * 获得课程详情带小节列表
     * @param userId
     * @param classId
     * @return
     */
    AppClassChildVO getClassChileVO(Long userId, Long classId);

    /**
     * 点赞
     * @param userId
     * @param appSpotOrCancelClassDTO
     */
    void userSpotClass(Long userId, AppSpotOrCancelClassDTO appSpotOrCancelClassDTO);

    /**
     * 用户发评论
     * @param userId
     * @param appClassCommentDTO
     */
    void userCreateComment(Long userId, AppClassCommentDTO appClassCommentDTO);

    /**
     * 导师回复评论
     * @param userId
     * @param appClassReplyCommentDTO
     */
    void dietitionReplyComment(Long userId, AppClassReplyCommentDTO appClassReplyCommentDTO);


    /**
     * 获取导师相关信息
     * @param appDietitionInfoDTO
     * @return
     */
    AppDietitionInfoVo  getDietitionInfoData(AppDietitionInfoDTO appDietitionInfoDTO);



    PageResult<DietitionInfoRespVO> getDietitionInfoList(@Param("appDietitionInfoDTO") AppUserClassDTO appUserClassDTO);
}
