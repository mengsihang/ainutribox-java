package com.ainutribox.module.product.controller.app.spu;

import cn.hutool.core.collection.CollUtil;
import com.ainutribox.framework.common.enums.CommonStatusEnum;
import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.module.member.api.level.MemberLevelApi;
import com.ainutribox.module.member.api.level.dto.MemberLevelRespDTO;
import com.ainutribox.module.member.api.user.MemberUserApi;
import com.ainutribox.module.member.api.user.dto.MemberUserRespDTO;
import com.ainutribox.module.product.controller.app.spu.vo.AppProductSpuDetailRespVO;
import com.ainutribox.module.product.controller.app.spu.vo.AppProductSpuPageReqVO;
import com.ainutribox.module.product.controller.app.spu.vo.AppProductSpuRespVO;
import com.ainutribox.module.product.controller.app.spu.vo.CategoryWithProductsRespVO;
import com.ainutribox.module.product.dal.dataobject.category.ProductCategoryDO;
import com.ainutribox.module.product.dal.dataobject.sku.ProductSkuDO;
import com.ainutribox.module.product.dal.dataobject.spu.ProductSpuDO;
import com.ainutribox.module.product.dal.dataobject.tags.TagsDO;
import com.ainutribox.module.product.dal.mysql.spu.ProductSpuMapper;
import com.ainutribox.module.product.enums.spu.ProductSpuStatusEnum;
import com.ainutribox.module.product.service.category.ProductCategoryService;
import com.ainutribox.module.product.service.history.ProductBrowseHistoryService;
import com.ainutribox.module.product.service.sku.ProductSkuService;
import com.ainutribox.module.product.service.spu.ProductSpuService;
import com.ainutribox.module.product.service.tags.TagsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.framework.common.pojo.CommonResult.success;
import static com.ainutribox.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.ainutribox.module.product.enums.ErrorCodeConstants.*;

@Tag(name = "用户 APP - 商品 SPU")
@RestController
@RequestMapping("/product/spu")
@Validated
public class AppProductSpuController {

    @Resource
    private ProductSpuService productSpuService;
    @Resource
    private ProductSkuService productSkuService;
    @Resource
    private ProductBrowseHistoryService productBrowseHistoryService;

    @Resource
    private MemberLevelApi memberLevelApi;
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private ProductSpuMapper productSpuMapper;
    @Resource
    private ProductCategoryService categoryService;
    @Resource
    private TagsService tagsService;

    @GetMapping("/list-by-ids")
    @Operation(summary = "获得商品 SPU 列表")
    @Parameter(name = "ids", description = "编号列表", required = true)
    public CommonResult<List<AppProductSpuRespVO>> getSpuList(@RequestParam("ids") Set<Long> ids) {
        List<ProductSpuDO> list = productSpuService.getSpuList(ids);
        if (CollUtil.isEmpty(list)) {
            return success(Collections.emptyList());
        }

        // 拼接返回
        list.forEach(spu -> spu.setSalesCount(spu.getSalesCount() + spu.getVirtualSalesCount()));
        List<AppProductSpuRespVO> voList = BeanUtils.toBean(list, AppProductSpuRespVO.class);
        // 处理 vip 价格
        MemberLevelRespDTO memberLevel = getMemberLevel();
        voList.forEach(vo -> vo.setVipPrice(calculateVipPrice(vo.getPrice(), memberLevel)));
        return success(voList);
    }

    @GetMapping("/page")
    @Operation(summary = "获得商品 SPU 分页")
    public CommonResult<PageResult<AppProductSpuRespVO>> getSpuPage(@Valid AppProductSpuPageReqVO pageVO) {
        PageResult<ProductSpuDO> pageResult = productSpuService.getSpuPage(pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }

        // 拼接返回
        pageResult.getList().forEach(spu -> spu.setSalesCount(spu.getSalesCount() + spu.getVirtualSalesCount()));
        PageResult<AppProductSpuRespVO> voPageResult = BeanUtils.toBean(pageResult, AppProductSpuRespVO.class);
        // 处理 vip 价格
        MemberLevelRespDTO memberLevel = getMemberLevel();
        voPageResult.getList().forEach(vo -> vo.setVipPrice(calculateVipPrice(vo.getPrice(), memberLevel)));
        return success(voPageResult);
    }

    @GetMapping("/get-detail")
    @Operation(summary = "获得商品 SPU 明细")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<AppProductSpuDetailRespVO> getSpuDetail(@RequestParam("id") Long id) {
        // 获得商品 SPU
        ProductSpuDO spu = productSpuService.getSpu(id);
        if (spu == null) {
            throw exception(SPU_NOT_EXISTS);
        }
        if (!ProductSpuStatusEnum.isEnable(spu.getStatus())) {
            throw exception(SPU_NOT_ENABLE, spu.getName());
        }
        

        // 获得商品 SKU
        List<ProductSkuDO> skus = productSkuService.getSkuListBySpuId(spu.getId());

        // 标签列表
        List<TagsDO> tagsList = tagsService.getTagsListByStatus(CommonStatusEnum.ENABLE.getStatus());


        // 增加浏览量
        productSpuService.updateBrowseCount(id, 1);
        // 保存浏览记录
        productBrowseHistoryService.createBrowseHistory(getLoginUserId(), id);

        // 拼接返回
        spu.setBrowseCount(spu.getBrowseCount() + spu.getVirtualSalesCount());
        spu.setSalesCount(spu.getSalesCount() + spu.getVirtualSalesCount());
        AppProductSpuDetailRespVO spuVO = BeanUtils.toBean(spu, AppProductSpuDetailRespVO.class)
                .setSkus(BeanUtils.toBean(skus, AppProductSpuDetailRespVO.Sku.class));
        // 处理 vip 价格
        MemberLevelRespDTO memberLevel = getMemberLevel();
        spuVO.setVipPrice(calculateVipPrice(spuVO.getPrice(), memberLevel));


        // 将标签ID和标签名称映射成一个 Map
        Map<Long, String> tagsMap = tagsList.stream()
                .collect(Collectors.toMap(TagsDO::getId, TagsDO::getName));

        List<Long> tagIds = spu.getKeyword();
        List<String> tagNames = tagIds.stream()
                .map(tagsMap::get)
                .toList();
        spuVO.setKeyword(tagNames.toString());

        return success(spuVO);
    }

    private MemberLevelRespDTO getMemberLevel() {
        Long userId = getLoginUserId();
        if (userId == null) {
            return null;
        }
        MemberUserRespDTO user = memberUserApi.getUser(userId);
        if (user.getLevelId() == null || user.getLevelId() <= 0) {
            return null;
        }
        return memberLevelApi.getMemberLevel(user.getLevelId());
    }

    /**
     * 计算会员 VIP 优惠价格
     *
     * @param price 原价
     * @param memberLevel 会员等级
     * @return 优惠价格
     */
    public Integer calculateVipPrice(Integer price, MemberLevelRespDTO memberLevel) {
        if (memberLevel == null || memberLevel.getDiscountPercent() == null) {
            return 0;
        }
        Integer newPrice = price * memberLevel.getDiscountPercent() / 100;
        return price - newPrice;
    }

    @GetMapping("/list")
    @Operation(summary = "获得商品 SPU")
    public CommonResult<List<CategoryWithProductsRespVO>> getSpuList(Integer productType) {

        if (null == productType) {
            throw exception(CATEGORY_NULL_EXISTS);
        }

        // 商品列表
        List<ProductSpuDO> productSpuDOList = productSpuMapper.selectList(
                new LambdaQueryWrapperX<ProductSpuDO>()
                        .eqIfPresent(ProductSpuDO::getProductType, productType)
                        .eq(ProductSpuDO::getStatus, ProductSpuStatusEnum.ENABLE.getStatus())
        );

        if (CollUtil.isEmpty(productSpuDOList)) {
            return success(null);
        }

        // 分类列表
        List<ProductCategoryDO> categoryList = categoryService.getEnableCategoryList();

        // 标签列表
        List<TagsDO> tagsList = tagsService.getTagsListByStatus(CommonStatusEnum.ENABLE.getStatus());

        // 将标签ID和标签名称映射成一个 Map
        Map<Long, String> tagsMap = tagsList.stream()
                .collect(Collectors.toMap(TagsDO::getId, TagsDO::getName));

        // 拼接返回
        productSpuDOList.forEach(spu -> spu.setSalesCount(spu.getSalesCount() + spu.getVirtualSalesCount()));

        List<AppProductSpuRespVO> voList = BeanUtils.toBean(productSpuDOList, AppProductSpuRespVO.class);

        // 处理 vip 价格
        MemberLevelRespDTO memberLevel = getMemberLevel();
        voList.forEach(vo -> vo.setVipPrice(calculateVipPrice(vo.getPrice(), memberLevel)));

        // 转化商品中的标签ID数组为标签名称数组
        voList.forEach(vo -> {
            if (vo.getKeyword() != null && !vo.getKeyword().isEmpty()) {
                // 去除方括号和空格，然后拆分
                String cleanedKeyword = vo.getKeyword().replaceAll("[\\[\\]\\s]", "");
                List<Long> tagIds = Arrays.stream(cleanedKeyword.split(","))
                        .map(Long::valueOf)
                        .toList();
                List<String> tagNames = tagIds.stream()
                        .map(tagsMap::get)
                        .toList();
                vo.setKeyword(tagNames.toString());
            }
        });

        // 将商品列表按分类分组，并组装成新的 DTO
        Map<Long, CategoryWithProductsRespVO> groupedProductMap = voList.stream().collect(Collectors.groupingBy(
                AppProductSpuRespVO::getCategoryId,
                Collectors.collectingAndThen(Collectors.toList(), products -> {
                    ProductCategoryDO category = categoryList.stream()
                            .filter(cat -> cat.getId().equals(products.get(0).getCategoryId()))
                            .findFirst()
                            .orElse(null);

                    CategoryWithProductsRespVO dto = new CategoryWithProductsRespVO();
                    dto.setCategory(category);
                    dto.setProducts(products);
                    return dto;
                })
        ));

        // 将结果转换成列表形式
        List<CategoryWithProductsRespVO> resultList = new ArrayList<>(groupedProductMap.values());

        return success(resultList);
    }





    // TODO 芋艿：商品的浏览记录；
}
