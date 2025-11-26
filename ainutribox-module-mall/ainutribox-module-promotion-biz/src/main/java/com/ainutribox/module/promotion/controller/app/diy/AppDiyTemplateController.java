package com.ainutribox.module.promotion.controller.app.diy;

import cn.hutool.core.util.ObjectUtil;
import com.ainutribox.framework.common.exception.ErrorCode;
import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.module.promotion.controller.app.diy.vo.AppDiyTemplatePropertyRespVO;
import com.ainutribox.module.promotion.controller.app.diy.vo.AppHomeTemplateRespVO;
import com.ainutribox.module.promotion.convert.diy.DiyTemplateConvert;
import com.ainutribox.module.promotion.dal.dataobject.article.ArticleDO;
import com.ainutribox.module.promotion.dal.dataobject.diy.DiyPageDO;
import com.ainutribox.module.promotion.dal.dataobject.diy.DiyTemplateDO;
import com.ainutribox.module.promotion.enums.diy.DiyPageEnum;
import com.ainutribox.module.promotion.service.article.ArticleService;
import com.ainutribox.module.promotion.service.diy.DiyPageService;
import com.ainutribox.module.promotion.service.diy.DiyTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.framework.common.pojo.CommonResult.success;
import static com.ainutribox.framework.common.util.collection.CollectionUtils.findFirst;

@Tag(name = "用户 APP - 装修模板")
@RestController
@RequestMapping("/promotion/diy-template")
@Validated
public class AppDiyTemplateController {

    @Resource
    private DiyTemplateService diyTemplateService;
    @Resource
    private DiyPageService diyPageService;

    @Resource
    private ArticleService articleService;

    // TODO @疯狂：要不要把 used 和 get 接口合并哈；不传递 id，直接拿默认；
    @GetMapping("/used")
    @Operation(summary = "使用中的装修模板")
    public CommonResult<AppDiyTemplatePropertyRespVO> getUsedDiyTemplate() {
        DiyTemplateDO diyTemplate = diyTemplateService.getUsedDiyTemplate();
        return success(buildVo(diyTemplate));
    }

    @GetMapping("/get")
    @Operation(summary = "获得装修模板")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppDiyTemplatePropertyRespVO> getDiyTemplate(@RequestParam("id") Long id) {
        DiyTemplateDO diyTemplate = diyTemplateService.getDiyTemplate(id);
        return success(buildVo(diyTemplate));
    }

    private AppDiyTemplatePropertyRespVO buildVo(DiyTemplateDO diyTemplate) {
        if (diyTemplate == null) {
            return null;
        }
        // 查询模板下的页面
        List<DiyPageDO> pages = diyPageService.getDiyPageByTemplateId(diyTemplate.getId());
        String home = findFirst(pages, page -> DiyPageEnum.INDEX.getName().equals(page.getName()), DiyPageDO::getProperty);
        String user = findFirst(pages, page -> DiyPageEnum.MY.getName().equals(page.getName()), DiyPageDO::getProperty);
        // 拼接返回
        return DiyTemplateConvert.INSTANCE.convertPropertyVo2(diyTemplate, home, user);
    }

    @GetMapping("/getHome")
    @Operation(summary = "获得首页数据")
    public CommonResult<AppHomeTemplateRespVO> getDiyTemplate() {

        AppHomeTemplateRespVO AppHomeTemplateRespVO = new AppHomeTemplateRespVO();

        //使用中的轮播图
        DiyTemplateDO diyTemplate = diyTemplateService.getDiyTemplate(1L);

        //使用中的首页文章
        ArticleDO articleDO = articleService.getArticle(1L);

        AppHomeTemplateRespVO.setPreviewPicUrls(diyTemplate.getPreviewPicUrls());
        AppHomeTemplateRespVO.setContent(articleDO.getContent());
        AppHomeTemplateRespVO.setRemark(diyTemplate.getRemark());


        return success(AppHomeTemplateRespVO);
    }

    @GetMapping("/getDiyNotice")
    @Operation(summary = "获得diy通知")
    public CommonResult<AppHomeTemplateRespVO> getDiyNotice(Integer type) {

        if(ObjectUtil.isNull(type)){
            throw exception(new ErrorCode(300,"type不能为空"));
        }

        AppHomeTemplateRespVO AppHomeTemplateRespVO = new AppHomeTemplateRespVO();

        //type=1的首页文章 2系统 3地址
        ArticleDO articleDO = articleService.getArticle(type.longValue());
        if(ObjectUtil.isNotEmpty(articleDO)){
            AppHomeTemplateRespVO.setContent(articleDO.getContent());
        }

        return success(AppHomeTemplateRespVO);
    }

    @GetMapping("/getDiyTemplate")
    @Operation(summary = "获得diy多图模板")
    public CommonResult<AppHomeTemplateRespVO> getDiyTemplate(Integer type) {

        if(ObjectUtil.isNull(type)){
            throw exception(new ErrorCode(300,"type不能为空"));
        }

        AppHomeTemplateRespVO AppHomeTemplateRespVO = new AppHomeTemplateRespVO();

        //多图片模板1首页
        DiyTemplateDO diyTemplate = diyTemplateService.getDiyTemplate(type.longValue());
        if(ObjectUtil.isNotEmpty(diyTemplate)){
            AppHomeTemplateRespVO.setPreviewPicUrls(diyTemplate.getPreviewPicUrls());
            AppHomeTemplateRespVO.setContent(diyTemplate.getRemark());
        }


        return success(AppHomeTemplateRespVO);
    }

}
