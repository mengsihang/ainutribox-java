package com.ainutribox.module.infra.service.video;

import com.ainutribox.module.infra.controller.app.file.vo.VideoFileResVO;

import java.util.List;

/**
 * AppVideoService
 *
 * @author lucifer
 * @date 2024-11-12 13:53
 */
public interface AppVideoService {

    List<VideoFileResVO> getVideo();

}
