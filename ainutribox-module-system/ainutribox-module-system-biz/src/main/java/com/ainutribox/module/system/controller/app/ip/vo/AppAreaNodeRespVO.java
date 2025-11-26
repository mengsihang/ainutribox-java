package com.ainutribox.module.system.controller.app.ip.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "用户 App - 地区节点 Response VO")
@Data
public class AppAreaNodeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    private Integer id;

    @Schema(description = "名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "北京")
    private String name;

    /**
     * 子节点
     */
    private List<AppAreaNodeRespVO> children;


    /**
     * 根据名字查找对应的 ID
     *
     * @param nodes 节点列表
     * @param targetName 目标名字
     * @return 对应的 ID，若未找到则返回 null
     */
    public static Integer findIdByName(List<AppAreaNodeRespVO> nodes, String targetName) {
        if (nodes == null || targetName == null) {
            return null;
        }

        for (AppAreaNodeRespVO node : nodes) {
            if (targetName.equals(node.getName())) {
                return node.getId();
            }
            Integer id = findIdByName(node.getChildren(), targetName);
            if (id != null) {
                return id;
            }
        }
        return null;
    }

}
