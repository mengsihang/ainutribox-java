package com.ainutribox.module.member.controller.app.vippackage;


import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.module.member.controller.admin.vippackage.vo.VipPackageRespVO;
import com.ainutribox.module.member.dal.dataobject.vippackage.VipPackageDO;
import com.ainutribox.module.member.dal.mysql.vippackage.VipPackageMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ainutribox.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 App - vip套餐")
@RestController
@RequestMapping("/member/vip-package")
@Validated
public class AppVipPackageController {

    @Resource
    private VipPackageMapper vipPackageMapper;

    @GetMapping("/get")
    @Operation(summary = "获得vip套餐列表")
    public CommonResult<List<VipPackageRespVO>> getVipPackage() {
        List<VipPackageDO> vipPackageDOList = vipPackageMapper.selectList();
        return success(BeanUtils.toBean(vipPackageDOList, VipPackageRespVO.class));
    }



}