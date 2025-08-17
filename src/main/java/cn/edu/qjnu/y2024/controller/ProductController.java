package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.Products;
import cn.edu.qjnu.y2024.service.IProductsService;
import cn.edu.qjnu.y2024.util.ElResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Resource
    private IProductsService productService;

    // 添加安全头
    private void addSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
    }

    // 分页查询
    @GetMapping("/page")
    public ElResult<Products> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       HttpServletResponse response) {
        addSecurityHeaders(response);

        Page<Products> page = new Page<>(pageNum, pageSize);
        // 注意：为了在列表页显示分类名称，建议在 IProductsService 的 page 方法中进行多表连接查询
        Page<Products> productPage = productService.page(page, new QueryWrapper<>());
        return ElResult.ok(productPage.getTotal(), productPage.getRecords());
    }

    // 新增
    @PostMapping("/add")
    public ElResult<Void> add(@RequestBody Products product) {
        // 前端会传来包含 categoryId 的 product 对象
        productService.save(product);
        return ElResult.ok();
    }

    // 更新
    @PostMapping("/update")
    public ElResult<Void> update(@RequestBody Products product) {
        // 前端会传来包含 categoryId 的 product 对象
        productService.updateById(product);
        return ElResult.ok();
    }

    // 删除单个
    @PostMapping("/delete")
    public ElResult<Void> delete(@RequestBody Products product) {
        productService.removeById(product.getProductId());
        return ElResult.ok();
    }

    // 批量删除 (修改为直接接收ID列表)
    @PostMapping("/batchDelete")
    public ElResult<Void> batchDelete(@RequestBody List<String> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return ElResult.error("productIds 不能为空");
        }
        productService.removeByIds(productIds);
        return ElResult.ok();
    }
}
