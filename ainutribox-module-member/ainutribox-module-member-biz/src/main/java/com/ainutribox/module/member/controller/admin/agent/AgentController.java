package com.ainutribox.module.member.controller.admin.agent;

import com.ainutribox.module.member.controller.admin.user.vo.MemberUserRespVO;
import com.ainutribox.module.member.dal.dataobject.user.MemberUserDO;
import com.ainutribox.module.member.service.user.MemberUserService;
import com.ainutribox.module.member.enums.ErrorCodeConstants;
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

import com.ainutribox.module.member.controller.admin.agent.vo.*;
import com.ainutribox.module.member.dal.dataobject.agent.AgentDO;
import com.ainutribox.module.member.service.agent.AgentService;

@Tag(name = "管理后台 - 代理商记录")
@RestController
@RequestMapping("/member/agent")
@Validated
public class AgentController {

    @Resource
    private AgentService agentService;
    @Resource
    private MemberUserService memberUserService;

    @PostMapping("/create")
    @Operation(summary = "创建代理商记录")
    @PreAuthorize("@ss.hasPermission('member:agent:create')")
    public CommonResult<Long> createAgent(@Valid @RequestBody AgentSaveReqVO createReqVO) {
        MemberUserDO memberUserDO = memberUserService.getUser(createReqVO.getUserId());
        //当用户基础信息不存在时，不能申请
        if (memberUserDO == null) {
            return CommonResult.error(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        AgentDO agentDO = agentService.getAgentByUserId(createReqVO.getUserId());
        //当申请记录存在时，修改已存在记录
        if (agentDO!= null) {
            createReqVO.setId(agentDO.getId());
            agentService.updateAgent(createReqVO);
            return success(agentDO.getId());
        }
        return success(agentService.createAgent(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新代理商记录")
    @PreAuthorize("@ss.hasPermission('member:agent:update')")
    public CommonResult<Boolean> updateAgent(@Valid @RequestBody AgentSaveReqVO updateReqVO) {
        agentService.updateAgent(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除代理商记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:agent:delete')")
    public CommonResult<Boolean> deleteAgent(@RequestParam("id") Long id) {
        agentService.deleteAgent(id);
        return success(true);
    }

    @PutMapping("/ok")
    @Operation(summary = "审核代理商记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:agent:ok')")
    public CommonResult<Boolean> ok(@RequestParam("id") Long id) {
        AgentSaveReqVO updateReqVO = new AgentSaveReqVO();
        updateReqVO.setId(id);
        updateReqVO.setStatus(updateReqVO.status_pass);
        agentService.updateAgent(updateReqVO);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得代理商记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:agent:query')")
    public CommonResult<AgentRespVO> getAgent(@RequestParam("id") Long id) {
        AgentDO agent = agentService.getAgent(id);
        return success(BeanUtils.toBean(agent, AgentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得代理商记录分页")
    @PreAuthorize("@ss.hasPermission('member:agent:query')")
    public CommonResult<PageResult<AgentRespVO>> getAgentPage(@Valid AgentPageReqVO pageReqVO) {
        PageResult<AgentDO> pageResult = agentService.getAgentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AgentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出代理商记录 Excel")
    @PreAuthorize("@ss.hasPermission('member:agent:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportAgentExcel(@Valid AgentPageReqVO pageReqVO,
                                 HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AgentDO> list = agentService.getAgentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "代理商记录.xls", "数据", AgentRespVO.class,
                BeanUtils.toBean(list, AgentRespVO.class));
    }

}