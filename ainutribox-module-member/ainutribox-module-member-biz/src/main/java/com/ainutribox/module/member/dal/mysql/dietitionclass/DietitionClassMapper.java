package com.ainutribox.module.member.dal.mysql.dietitionclass;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.controller.admin.dietition.vo.DietitionInfoRespVO;
import com.ainutribox.module.member.controller.app.dietitionclass.dto.AppDietitionInfoDTO;
import com.ainutribox.module.member.controller.app.dietitionclass.dto.AppUserClassDTO;
import com.ainutribox.module.member.controller.app.dietitionclass.vo.AppUserClassVO;
import com.ainutribox.module.member.controller.app.dietitionclass.vo.DietitianProductVO;
import com.ainutribox.module.member.dal.dataobject.dietitionclass.DietitionClassDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.dietitionclass.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 营养师课程 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface DietitionClassMapper extends BaseMapperX<DietitionClassDO> {

    default PageResult<DietitionClassDO> selectPage(DietitionClassPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DietitionClassDO>()
                .likeIfPresent(DietitionClassDO::getName, reqVO.getName())
                .eqIfPresent(DietitionClassDO::getPicUrl, reqVO.getPicUrl())
                .eqIfPresent(DietitionClassDO::getSort, reqVO.getSort())
                .eqIfPresent(DietitionClassDO::getDescription, reqVO.getDescription())
                .eqIfPresent(DietitionClassDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DietitionClassDO::getDietitionId, reqVO.getDietitionId())
                .eqIfPresent(DietitionClassDO::getCourseTitle, reqVO.getCourseTitle())
                .eqIfPresent(DietitionClassDO::getClassUrl, reqVO.getClassUrl())
                .eqIfPresent(DietitionClassDO::getClassType, reqVO.getClassType())
                .eqIfPresent(DietitionClassDO::getInfoPic, reqVO.getInfoPic())
                .eqIfPresent(DietitionClassDO::getTags, reqVO.getTags())
                .eqIfPresent(DietitionClassDO::getCommentNum, reqVO.getCommentNum())
                .eqIfPresent(DietitionClassDO::getLikeNum, reqVO.getLikeNum())
                .eqIfPresent(DietitionClassDO::getScore, reqVO.getScore())
                .eqIfPresent(DietitionClassDO::getActualPeople, reqVO.getActualPeople())
                .eqIfPresent(DietitionClassDO::getVirtualPeople, reqVO.getVirtualPeople())
                .betweenIfPresent(DietitionClassDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DietitionClassDO::getPrice, reqVO.getPrice())
                .eqIfPresent(DietitionClassDO::getVipPrice, reqVO.getVipPrice())
                .eqIfPresent(DietitionClassDO::getCategoryId, reqVO.getCategoryId())
                .orderByDesc(DietitionClassDO::getId));
    }

    /**
     * 获取课程分页
     * @param page
     * @param appUserClassDTO
     * @return
     */
    IPage<AppUserClassVO> selectUserClassPage(Page<?> page, @Param("classDTO") AppUserClassDTO appUserClassDTO);

    /**
     * 获取用户加入课程
     * @param userId
     * @return
     */
    List<AppUserClassVO> selectUserJoinClassList(@Param("userId") Long userId);

    /**
     * 获取用户购买课程
     * @param userId
     * @return
     */
    List<AppUserClassVO> selectUserBuyClassList(@Param("userId") Long userId);

    /**
     * 查询课程详情
     * @param classId
     * @return
     */
    AppUserClassVO selectClassVo(@Param("classId") Long classId);

    /**
     * 获取课程评分平均值
     * @param classId
     * @return
     */
    Integer getClassAverageScore(@Param("classId") Long classId);

    /**
     * 获取老师课程列表
     * @param
     * @param appDietitionInfoDTO
     * @return
     */
    List<AppUserClassVO> selectDietitionClassList(@Param("classDTO") AppDietitionInfoDTO appDietitionInfoDTO);

    /**
     * 获得老师商品列表
     * @param dietitionId
     * @return
     */
    List<DietitianProductVO> selectDietitionProductList(@Param("dietitionId") Long dietitionId);


    IPage<DietitionInfoRespVO> getDietitionInfoList(Page<?> page,@Param("appDietitionInfoDTO") AppUserClassDTO appUserClassDTO);

}