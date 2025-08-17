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
     * 新增：获取全部分类列表 (用于前端下拉菜单)
     */
    @GetMapping("/list")
    public ElResult<ProductCategories> listAll(HttpServletResponse response) {
        addSecurityHeaders(response);
        List<ProductCategories> list = productCategoriesService.list();
        return ElResult.ok((long) list.size(), list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public ElResult<ProductCategories> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
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
    public ElResult<Void> add(@RequestBody ProductCategories productCategory) {
        productCategoriesService.save(productCategory);
        return ElResult.ok();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public ElResult<Void> update(@RequestBody ProductCategories productCategory) {
        productCategoriesService.updateById(productCategory);
        return ElResult.ok();
    }

    /**
     * 删除单个
     */
    @PostMapping("/delete")
    public ElResult<Void> delete(@RequestBody ProductCategories productCategory) {
        productCategoriesService.removeById(productCategory.getCategoryId());
        return ElResult.ok();
    }

    /**
     * 批量删除 (修改为直接接收ID列表)
     */
    @PostMapping("/batchDelete")
    public ElResult<Void> batchDelete(@RequestBody List<String> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return ElResult.error("categoryIds 不能为空");
        }
        productCategoriesService.removeByIds(categoryIds);
        return ElResult.ok();
    }
}
