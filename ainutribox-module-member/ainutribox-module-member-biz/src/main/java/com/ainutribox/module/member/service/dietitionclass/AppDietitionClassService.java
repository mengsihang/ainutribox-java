package com.ainutribox.module.member.service.dietitionclass;

import com.ainutribox.module.member.controller.admin.dietitionclass.vo.ClassUpdateStatusReqVO;
import com.ainutribox.module.member.dal.dataobject.dietitionclasschild.DietitionClassChildDO;

import java.util.List;

public interface AppDietitionClassService {

    /**
     * 更新营养师自组包状态 上架 下架
     *
     * @param updateReqVO
     */
    void updateSpuStatus(ClassUpdateStatusReqVO updateReqVO);

    /**
     * 获取对应课程所有小节
     * @param classId
     * @return
     */
    List<DietitionClassChildDO>  getChildList(Long classId);

    /**
     * 更新课程人数
     * @param classId
     * @param number
     */
    void updateClassPeople(Long classId,Integer number);


    /**
     * 判断课程是否存在
     */
    Integer isClassExpired(Long ClassId);


    /**
     * 更新点赞人数
     * @param classId
     * @param number
     */
    void updateClassSpot(Long classId,Integer number);


    /**
     * 更新评论评分
     * @param classId
     * @param number
     */
    void updateClassAvg(Long classId,Integer number);
}
