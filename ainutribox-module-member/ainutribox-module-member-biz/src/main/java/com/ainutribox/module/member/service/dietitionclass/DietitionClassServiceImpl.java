package com.ainutribox.module.member.service.dietitionclass;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.module.member.controller.admin.dietitionclass.vo.DietitionClassPageReqVO;
import com.ainutribox.module.member.controller.admin.dietitionclass.vo.DietitionClassSaveReqVO;
import com.ainutribox.module.member.dal.dataobject.dietitionclass.DietitionClassDO;
import com.ainutribox.module.member.dal.mysql.dietitionclass.DietitionClassMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.DIETITION_CLASS_NOT_EXISTS;

/**
 * 营养师课程 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class DietitionClassServiceImpl implements DietitionClassService {

    @Resource
    private DietitionClassMapper dietitionClassMapper;

    @Override
    public Long createDietitionClass(DietitionClassSaveReqVO createReqVO) {
        // 插入
        DietitionClassDO dietitionClass = BeanUtils.toBean(createReqVO, DietitionClassDO.class);
        dietitionClassMapper.insert(dietitionClass);
        // 返回
        return dietitionClass.getId();
    }

    @Override
    public void updateDietitionClass(DietitionClassSaveReqVO updateReqVO) {
        // 校验存在
        validateDietitionClassExists(updateReqVO.getId());
        // 更新
        DietitionClassDO updateObj = BeanUtils.toBean(updateReqVO, DietitionClassDO.class);
        dietitionClassMapper.updateById(updateObj);
    }

    @Override
    public void deleteDietitionClass(Long id) {
        // 校验存在
        validateDietitionClassExists(id);
        // 删除
        dietitionClassMapper.deleteById(id);
    }

    private void validateDietitionClassExists(Long id) {
        if (dietitionClassMapper.selectById(id) == null) {
            throw exception(DIETITION_CLASS_NOT_EXISTS);
        }
    }

    @Override
    public DietitionClassDO getDietitionClass(Long id) {
        return dietitionClassMapper.selectById(id);
    }

    @Override
    public PageResult<DietitionClassDO> getDietitionClassPage(DietitionClassPageReqVO pageReqVO) {
        return dietitionClassMapper.selectPage(pageReqVO);
    }

}