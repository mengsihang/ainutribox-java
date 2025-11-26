package com.ainutribox.module.member.controller.app.dietitionclasscategory;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.security.core.annotations.PreAuthenticated;
import com.ainutribox.module.member.controller.admin.dietitionclasscategory.vo.DietitionClassCategoryRespVO;
import com.ainutribox.module.member.dal.dataobject.dietitionclasscategory.DietitionClassCategoryDO;
import com.ainutribox.module.member.dal.mysql.dietitionclasscategory.DietitionClassCategoryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ainutribox.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 营养师课程分类")
@RestController
@RequestMapping("/member/dietition-class-category")
@Validated
public class AppDietitionClassCategoryController {

    @Resource
    private DietitionClassCategoryMapper dietitionClassCategoryMapper;



    @GetMapping("/list")
    @Operation(summary = "获得营养师课程分类分页")
    public CommonResult<List<DietitionClassCategoryRespVO>> getDietitionClassCategoryList() {

        LambdaQueryWrapperX<DietitionClassCategoryDO>  cateWrapperx = new LambdaQueryWrapperX<>();
        cateWrapperx.eq(DietitionClassCategoryDO::getStatus, 0);

        List<DietitionClassCategoryDO> categoryDOList = dietitionClassCategoryMapper.selectList(cateWrapperx);
        return success(BeanUtils.toBean(categoryDOList, DietitionClassCategoryRespVO.class));
    }



}