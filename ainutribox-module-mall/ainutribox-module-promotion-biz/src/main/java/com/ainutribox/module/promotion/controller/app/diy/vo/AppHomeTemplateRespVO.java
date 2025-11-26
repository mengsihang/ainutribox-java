package com.ainutribox.module.promotion.controller.app.diy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import java.util.List;

@Schema(description = "用户 App - 首页数据 Response VO")
@Data
@ToString(callSuper = true)
public class AppHomeTemplateRespVO {

    @Schema(description = "首页轮播图")
    private List<String> previewPicUrls;
    @Schema(description = "首页富文本")
    private String content;
    @Schema(description = "备注")
    private String remark;
}
