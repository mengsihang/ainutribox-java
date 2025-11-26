package com.ainutribox.module.member.controller.admin.dietition;

import com.ainutribox.module.member.controller.admin.agent.vo.AgentSaveReqVO;
import com.ainutribox.module.member.dal.dataobject.agent.AgentDO;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionInfoDO;
import com.ainutribox.module.member.dal.dataobject.user.MemberUserDO;
import com.ainutribox.module.member.enums.ErrorCodeConstants;
import com.ainutribox.module.member.service.dietition.DietitionInfoService;
import com.ainutribox.module.member.service.user.MemberUserService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import static com.ainutribox.framework.common.pojo.CommonResult.success;

import com.ainutribox.framework.excel.core.util.ExcelUtils;

import com.ainutribox.framework.apilog.core.annotation.ApiAccessLog;
import static com.ainutribox.framework.apilog.core.enums.OperateTypeEnum.*;

import com.ainutribox.module.member.controller.admin.dietition.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionDO;
import com.ainutribox.module.member.service.dietition.DietitionService;

@Tag(name = "管理后台 - 营养师记录")
@RestController
@RequestMapping("/member/dietition")
@Validated
public class DietitionController {

    @Resource
    private DietitionService dietitionService;
    @Resource
    private MemberUserService memberUserService;
    @Resource
    private DietitionInfoService dietitionInfoService;


    @PostMapping("/create")
    @Operation(summary = "创建营养师记录")
    @PreAuthorize("@ss.hasPermission('member:dietition:create')")
    public CommonResult<Long> createDietition(@Valid @RequestBody DietitionSaveReqVO createReqVO) {
        MemberUserDO memberUserDO = memberUserService.getUser(createReqVO.getUserId());
        //当用户基础信息不存在时，不能申请
        if (memberUserDO == null) {
            return CommonResult.error(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        DietitionDO dietitionDO = dietitionService.getDietitionByUserId(createReqVO.getUserId());
        //当申请记录存在时，修改已存在记录
        if (dietitionDO!= null){
            createReqVO.setId(dietitionDO.getId());
            dietitionService.createDietition(createReqVO);
            return CommonResult.success(dietitionDO.getId());
        }
        return success(dietitionService.createDietition(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新营养师记录")
    @PreAuthorize("@ss.hasPermission('member:dietition:update')")
    public CommonResult<Boolean> updateDietition(@Valid @RequestBody DietitionSaveReqVO updateReqVO) {
        dietitionService.updateDietition(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除营养师记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:dietition:delete')")
    public CommonResult<Boolean> deleteDietition(@RequestParam("id") Long id) {
        dietitionService.deleteDietition(id);
        return success(true);
    }
    @PutMapping("/ok")
    @Operation(summary = "审核代理商记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:agent:ok')")
    public CommonResult<Boolean> ok(@RequestParam("id") Long id) {
        DietitionSaveReqVO updateReqVO = new DietitionSaveReqVO();
        updateReqVO.setId(id);
        updateReqVO.setStatus(updateReqVO.status_pass);
        dietitionService.updateDietition(updateReqVO);
        if (updateReqVO.getStatus().equals(1)){
            //同意的情况下初始化营养师表
            DietitionDO updateBeforDietition = dietitionService.getDietition(id);
            DietitionInfoDO dietitionByUser = dietitionInfoService.getDietitionByUserId(updateBeforDietition.getUserId());
            if (dietitionByUser == null){
                //如果不存则新增一条记录
                DietitionInfoSaveReqVO info = new DietitionInfoSaveReqVO();
                info.setUserId(updateBeforDietition.getUserId());
                //默认信息
                info.setNickName("营养师");
                info.setLevel(0);
                info.setScore(100L);
                info.setFavorableRate(1000L);
                info.setAdviceNum(0L);
                info.setCaseNum(0L);
                info.setExperience(0L);
                info.setContent("新入驻的营养师");
                info.setPhone(updateBeforDietition.getTel().toString());
                dietitionInfoService.createDietitionInfo(info);
            }
        }

        return success(true);
    }
    @GetMapping("/get")
    @Operation(summary = "获得营养师记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:dietition:query')")
    public CommonResult<DietitionRespVO> getDietition(@RequestParam("id") Long id) {
        DietitionDO dietition = dietitionService.getDietition(id);
        return success(BeanUtils.toBean(dietition, DietitionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得营养师记录分页")
    @PreAuthorize("@ss.hasPermission('member:dietition:query')")
    public CommonResult<PageResult<DietitionRespVO>> getDietitionPage(@Valid DietitionPageReqVO pageReqVO) {
        PageResult<DietitionDO> pageResult = dietitionService.getDietitionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DietitionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出营养师记录 Excel")
    @PreAuthorize("@ss.hasPermission('member:dietition:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDietitionExcel(@Valid DietitionPageReqVO pageReqVO,
                                     HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DietitionDO> list = dietitionService.getDietitionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "营养师记录.xls", "数据", DietitionRespVO.class,
                BeanUtils.toBean(list, DietitionRespVO.class));
    }

}
