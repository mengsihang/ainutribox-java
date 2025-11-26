package com.ainutribox.module.infra.controller.app.file.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * VideoFileResVO
 *
 * @author lucifer
 * @date 2024-11-12 13:47
 */
@Schema(description = "用户 App - 获取视频VO")
@Data
public class VideoFileResVO {

    private Long id;

    private String url;

}
