package com.ainutribox.module.infra.controller.app.file;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.module.infra.controller.app.file.vo.VideoFileResVO;
import com.ainutribox.module.infra.service.video.AppVideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * VideoFileController
 *
 * @author lucifer
 * @date 2024-11-12 13:40
 */
@Tag(name = "用户 App - 视频获取接口")
@RestController
@RequestMapping("/video/file")
@Validated
@Slf4j
public class VideoFileController {

    @Resource
    private AppVideoService appVideoService;

    @GetMapping("/getVideo")
    @Operation(summary = "获取指定视频")
    public CommonResult<List<VideoFileResVO>> getVideo(){
        return  CommonResult.success(appVideoService.getVideo());
    }


}
