package com.ainutribox.module.product.controller.app.dietitian;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.framework.security.core.annotations.PreAuthenticated;
import com.ainutribox.module.product.controller.admin.dietitian.vo.DietitianRespVO;
import com.ainutribox.module.product.controller.admin.dietitian.vo.DietitianSaveReqVO;
import com.ainutribox.module.product.controller.admin.spu.vo.ProductSpuUpdateStatusReqVO;
import com.ainutribox.module.product.dal.dataobject.dietitian.DietitianDO;
import com.ainutribox.module.product.enums.spu.DietitianSpuStatusEnum;
import com.ainutribox.module.product.service.dietitian.AppDietitianService;
import com.ainutribox.module.product.service.dietitian.DietitianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ainutribox.framework.common.pojo.CommonResult.success;
import static com.ainutribox.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 app - 营养师自组营养包")
@RestController
@RequestMapping("/product/dietitian")
@Validated
public class AppDietitianController {

    @Resource
    private DietitianService dietitianService;

    @Resource
    private AppDietitianService appDietitianService;

    @PostMapping("/create")
    @Operation(summary = "创建营养师自组营养包")
    @PreAuthenticated
    public CommonResult<Long> createDietitian(@Valid @RequestBody DietitianSaveReqVO createReqVO) {
        createReqVO.setDietitianId(getLoginUserId());
        return success(dietitianService.createDietitian(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新营养师自组营养包")
    @PreAuthenticated
    public CommonResult<Boolean> updateDietitian(@Valid @RequestBody DietitianSaveReqVO updateReqVO) {
        dietitianService.updateDietitian(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "更新商品 SPU Status")
    @PreAuthenticated
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody ProductSpuUpdateStatusReqVO updateReqVO) {
        appDietitianService.updateSpuStatus(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除营养师自组营养包")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthenticated
    public CommonResult<Boolean> deleteDietitian(@RequestParam("id") Long id) {
        dietitianService.deleteDietitian(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得营养师自组营养包")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthenticated
    public CommonResult<DietitianRespVO> getDietitian(@RequestParam("id") Long id) {
        DietitianDO dietitian = dietitianService.getDietitian(id);
        return success(BeanUtils.toBean(dietitian, DietitianRespVO.class));
    }

    @GetMapping("/getDietitianList")
    @Operation(summary = "获得营养师自组营养包")
    @PreAuthenticated
    public CommonResult<List<DietitianRespVO>> getDietitianList() {
        List<DietitianDO> dietitianDOList  = appDietitianService.getDietitianList(getLoginUserId(),DietitianSpuStatusEnum.Dietitian.getStatus());
        return success(BeanUtils.toBean(dietitianDOList, DietitianRespVO.class));
    }

    @GetMapping("/getUserDietitianList")
    @Operation(summary = "用户获得营养师自组营养包")
    @Parameter(name = "dietitianId", description = "营养师ID", required = true, example = "1024")
    @PreAuthenticated
    public CommonResult<List<DietitianRespVO>> getUserDietitianList(@RequestParam("dietitianId")Long dietitianId) {
        List<DietitianDO> dietitianDOList  = appDietitianService.getDietitianList(dietitianId,DietitianSpuStatusEnum.USER.getStatus());
        return success(BeanUtils.toBean(dietitianDOList, DietitianRespVO.class));
    }

}