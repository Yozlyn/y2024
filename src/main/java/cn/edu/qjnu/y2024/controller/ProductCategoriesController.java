package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.ProductCategories;
import cn.edu.qjnu.y2024.service.IProductCategoriesService;
import cn.edu.qjnu.y2024.util.ElResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productCategories")
@CrossOrigin
public class ProductCategoriesController {

    @Resource
    private IProductCategoriesService productCategoriesService;

    // 添加安全头
    private void addSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public ElResult findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             HttpServletResponse response) {
        addSecurityHeaders(response);
        Page<ProductCategories> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ProductCategories> queryWrapper = new QueryWrapper<>();
        Page<ProductCategories> productCategoriesPage = productCategoriesService.page(page, queryWrapper);
        return ElResult.ok(productCategoriesPage.getTotal(), productCategoriesPage.getRecords());
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public ElResult add(@RequestBody ProductCategories productCategory) {
        productCategoriesService.save(productCategory);
        return ElResult.ok();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public ElResult update(@RequestBody ProductCategories productCategory) {
        productCategoriesService.updateById(productCategory);
        return ElResult.ok();
    }

    /**
     * 删除单个
     */
    @PostMapping("/delete")
    public ElResult delete(@RequestBody ProductCategories productCategory) {
        productCategoriesService.removeById(productCategory.getCategoryId());
        return ElResult.ok();
    }

    /**
     * 批量删除
     */
    @PostMapping("/batchDelete")
    public ElResult batchDelete(@RequestBody Map<String, List<String>> body) {
        List<String> categoryIds = body.get("categoryIds");
        if (categoryIds == null || categoryIds.isEmpty()) {
            return ElResult.error("categoryIds 不能为空");
        }
        productCategoriesService.removeByIds(categoryIds);
        return ElResult.ok();
    }
}