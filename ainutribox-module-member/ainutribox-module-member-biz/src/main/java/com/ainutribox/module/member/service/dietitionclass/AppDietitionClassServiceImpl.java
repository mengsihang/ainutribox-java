package com.ainutribox.module.member.service.dietitionclass;

import cn.hutool.core.util.ObjectUtil;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.module.member.controller.admin.dietitionclass.vo.ClassUpdateStatusReqVO;
import com.ainutribox.module.member.dal.dataobject.dietitionclass.DietitionClassDO;
import com.ainutribox.module.member.dal.dataobject.dietitionclasschild.DietitionClassChildDO;
import com.ainutribox.module.member.dal.mysql.dietitionclass.DietitionClassMapper;
import com.ainutribox.module.member.dal.mysql.dietitionclasschild.DietitionClassChildMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.DIETITION_CLASS_CHILD_NOT_EXISTS;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.DIETITION_CLASS_NOT_EXISTS;

/**
 * AppDietitianClassServiceImpl
 *
 * @author lucifer
 * @date 2024-06-27 20:57
 */
@Service
public class AppDietitionClassServiceImpl implements AppDietitionClassService {

    @Resource
    private DietitionClassMapper classMapper;

    @Resource
    private DietitionClassChildMapper classChildMapper;

    @Override
    public void updateSpuStatus(ClassUpdateStatusReqVO updateReqVO) {
        // 校验存在
        validateSpuExists(updateReqVO.getId());
        //校验小节
        validateSpuExistsChild(updateReqVO.getId());
        // 更新状态
        DietitionClassDO dietitianDO = classMapper.selectById(updateReqVO.getId()).setStatus(updateReqVO.getStatus());
        classMapper.updateById(dietitianDO);
    }

    @Override
    public List<DietitionClassChildDO> getChildList(Long classId) {
        LambdaQueryWrapperX<DietitionClassChildDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(DietitionClassChildDO::getClassId,classId);
        return classChildMapper.selectList(lambdaQueryWrapperX);
    }

    @Override
    public void updateClassPeople(Long classId,Integer number) {
        //更新课程人数
        DietitionClassDO dietitionClassDO = classMapper.selectOne("id", classId);
        int peopleNum = dietitionClassDO.getActualPeople()+number;
        if(peopleNum <= 0)
            peopleNum = 0;
        dietitionClassDO.setActualPeople(peopleNum);
        classMapper.updateById(dietitionClassDO);
    }

    @Override
    public Integer isClassExpired(Long ClassId) {
        DietitionClassDO classDO = classMapper.selectById(ClassId);
        if(ObjectUtil.isEmpty(classDO) || classDO.getStatus() == 1){
            return 0;
        }
        return 1;
    }

    @Override
    public void updateClassSpot(Long classId, Integer number) {
        //更新点赞
        DietitionClassDO dietitionClassDO = classMapper.selectOne("id", classId);
        dietitionClassDO.setLikeNum((dietitionClassDO.getLikeNum()+number));
        classMapper.updateById(dietitionClassDO);
    }

    @Override
    public void updateClassAvg(Long classId, Integer number) {
        //更新评论
        DietitionClassDO dietitionClassDO = classMapper.selectOne("id", classId);
        dietitionClassDO.setScore(number);
        classMapper.updateById(dietitionClassDO);
    }


    private void validateSpuExists(Long id) {
        if (classMapper.selectById(id) == null) {
            throw exception(DIETITION_CLASS_NOT_EXISTS);
        }
    }

    private  void validateSpuExistsChild(Long id) {
        LambdaQueryWrapperX<DietitionClassChildDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(DietitionClassChildDO::getClassId,id);
        List<DietitionClassChildDO> classChildDOList = classChildMapper.selectList(lambdaQueryWrapperX);
        if (classChildDOList == null || classChildDOList.isEmpty()) {
            throw exception(DIETITION_CLASS_CHILD_NOT_EXISTS);
        }

    }
}
