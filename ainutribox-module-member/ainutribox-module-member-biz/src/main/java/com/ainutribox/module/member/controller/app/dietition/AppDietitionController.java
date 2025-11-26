package com.ainutribox.module.member.controller.app.dietition;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.framework.security.core.annotations.PreAuthenticated;
import com.ainutribox.module.member.controller.admin.dietition.vo.DietitionSaveReqVO;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionDO;
import com.ainutribox.module.member.dal.dataobject.user.MemberUserDO;
import com.ainutribox.module.member.enums.ErrorCodeConstants;
import com.ainutribox.module.member.service.dietition.DietitionService;
import com.ainutribox.module.member.service.user.MemberUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.ainutribox.framework.common.pojo.CommonResult.success;
import static com.ainutribox.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 营养师")
@RestController
@RequestMapping("/member/dietition")
@Validated
public class AppDietitionController {

    @Resource
    private DietitionService dietitionService;
    @Resource
    private MemberUserService memberUserService;

    @PostMapping("/create")
    @Operation(summary = "申请营养师")
    @PreAuthenticated
    public CommonResult<Long> createDietition(@Valid @RequestBody DietitionSaveReqVO createReqVO) {
        //防止篡改状态
        createReqVO.setStatus(0);
        createReqVO.setUserId(getLoginUserId());
        MemberUserDO memberUserDO = memberUserService.getUser(createReqVO.getUserId());
        if (memberUserDO == null) {
            return CommonResult.error(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        DietitionDO dietitionDO = dietitionService.getDietitionByUserId(createReqVO.getUserId());
        if (dietitionDO != null) {
            if(dietitionDO.getStatus() == 1){
                return CommonResult.error(ErrorCodeConstants.DIETITION_STATUS_ONE);
            }
            dietitionDO.setStatus(0);
            dietitionService.updateDietition(BeanUtils.toBean(dietitionDO,DietitionSaveReqVO.class));
            return success(1L);
        }else {
            return success(dietitionService.createDietition(createReqVO));
        }

    }



}