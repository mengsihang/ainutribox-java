package com.ainutribox.module.member.controller.app.dietitionclass.vo;

import com.ainutribox.module.member.controller.admin.dietitionclasschild.vo.DietitionClassChildRespVO;
import com.ainutribox.module.member.dal.dataobject.dietitionclasschild.DietitionClassChildDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * ClassChildVO
 *
 * @author lucifer
 * @date 2024-07-01 14:38
 */
@Data
public class AppClassChildVO {

    @Schema(description = "小节列表")
    private List<DietitionClassChildRespVO> classChildDOList;

    @Schema(description = "是否点赞")
    private Integer spotStatus;

    @Schema(description = "是否购买")
    private Integer payStatus;

    @Schema(description = "是否加入")
    private Integer joinStatus;

    @Schema(description = "课程详情")
    private AppUserClassVO appUserClassVO;

    @Schema(description = "课程评论数")
    private double commentNum;

}
