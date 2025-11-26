package com.ainutribox.module.infra.service.video;

import cn.hutool.core.util.ObjectUtil;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.module.infra.controller.app.file.vo.VideoFileResVO;
import com.ainutribox.module.infra.dal.dataobject.video.VideoDO;
import com.ainutribox.module.infra.dal.mysql.video.VideoMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * AppVideoServiceImpl
 *
 * @author lucifer
 * @date 2024-11-12 13:53
 */
@Service
public class AppVideoServiceImpl implements  AppVideoService{

    @Resource
    private VideoMapper videoMapper;


    @Override
    public List<VideoFileResVO> getVideo() {

        List<VideoFileResVO>  videoFileResVOList = new ArrayList<>();

        List<VideoDO> videoDOList = videoMapper.selectList();
        if(ObjectUtil.isNotEmpty(videoDOList)){
            videoFileResVOList = BeanUtils.toBean(videoDOList,VideoFileResVO.class);
        }

        return videoFileResVOList;
    }
}
