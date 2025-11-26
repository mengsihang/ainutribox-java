package com.ainutribox.module.member.service.dietitionclasschild;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.module.member.controller.admin.dietitionclasschild.vo.DietitionClassChildPageReqVO;
import com.ainutribox.module.member.controller.admin.dietitionclasschild.vo.DietitionClassChildSaveReqVO;
import com.ainutribox.module.member.dal.dataobject.dietitionclasschild.DietitionClassChildDO;
import com.ainutribox.module.member.dal.mysql.dietitionclasschild.DietitionClassChildMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.DIETITION_CLASS_CHILD_NOT_EXISTS;

/**
 * 课程小节 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class DietitionClassChildServiceImpl implements DietitionClassChildService {

    @Resource
    private DietitionClassChildMapper dietitionClassChildMapper;

    @Override
    public Long createDietitionClassChild(DietitionClassChildSaveReqVO createReqVO) {
        // 插入
        DietitionClassChildDO dietitionClassChild = BeanUtils.toBean(createReqVO, DietitionClassChildDO.class);
        /*if(StringUtil.isNotBlank(dietitionClassChild.getVideoUrl())){
            dietitionClassChild.setVideoTime(getVideoTime(dietitionClassChild.getVideoUrl()));
        }*/

        dietitionClassChildMapper.insert(dietitionClassChild);
        // 返回
        return dietitionClassChild.getId();
    }

    @Override
    public void updateDietitionClassChild(DietitionClassChildSaveReqVO updateReqVO) {
        // 校验存在
        DietitionClassChildDO classChildDO = validateDietitionClassChildExists(updateReqVO.getId());
        // 更新
        DietitionClassChildDO updateObj = BeanUtils.toBean(updateReqVO, DietitionClassChildDO.class);

        /*if(StringUtil.isNotBlank(updateObj.getVideoUrl()) && (!updateObj.getVideoUrl().equals(classChildDO.getVideoUrl()))){
            updateObj.setVideoTime(getVideoTime(updateObj.getVideoUrl()));
        }*/

        dietitionClassChildMapper.updateById(updateObj);
    }

    @Override
    public void deleteDietitionClassChild(Long id) {
        // 校验存在
        validateDietitionClassChildExists(id);
        // 删除
        dietitionClassChildMapper.deleteById(id);
    }

    private DietitionClassChildDO validateDietitionClassChildExists(Long id) {

        DietitionClassChildDO classChildDO = dietitionClassChildMapper.selectById(id);

        if (classChildDO == null) {
            throw exception(DIETITION_CLASS_CHILD_NOT_EXISTS);
        }

        return classChildDO;
    }

    @Override
    public DietitionClassChildDO getDietitionClassChild(Long id) {
        return dietitionClassChildMapper.selectById(id);
    }

    @Override
    public PageResult<DietitionClassChildDO> getDietitionClassChildPage(DietitionClassChildPageReqVO pageReqVO) {
        return dietitionClassChildMapper.selectPage(pageReqVO);
    }

    /*public int getVideoTime(String videoUrl){
        FFprobe ffprobe = FFprobe.atPath();
        // 执行 FFprobe 获取视频信息
        FFprobeResult result = ffprobe.setShowStreams(true)
                .setInput(videoUrl)
                .execute();
        // 获取视频时长（以秒为单位）
        double duration = result.getStreams().get(0).getDuration();
        // 将时长转换为整数秒
        return (int) Math.round(duration);
    }*/

}