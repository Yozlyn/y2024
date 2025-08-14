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
import java.util.Map;

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
    public ElResult findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             HttpServletResponse response) {
        addSecurityHeaders(response);
        if (pageNum == null || pageNum <= 0) pageNum = 1;
        if (pageSize == null || pageSize <= 0) pageSize = 10;

        Page<Products> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Products> queryWrapper = new QueryWrapper<>();
        Page<Products> productPage = productService.page(page, queryWrapper);
        return ElResult.ok(productPage.getTotal(), productPage.getRecords());
    }

    // 新增
    @PostMapping("/add")
    public ElResult add(@RequestBody Products product) {
        productService.save(product);
        return ElResult.ok();
    }

    // 更新
    @PostMapping("/update")
    public ElResult update(@RequestBody Products product) {
        productService.updateById(product);
        return ElResult.ok();
    }

    // 删除单个
    @PostMapping("/delete")
    public ElResult delete(@RequestBody Products product) {
        productService.removeById(product.getProductId());
        return ElResult.ok();
    }

    // 批量删除
    @PostMapping("/batchDelete")
    public ElResult batchDelete(@RequestBody Map<String, List<String>> body) {
        List<String> productIds = body.get("productIds");
        if (productIds == null || productIds.isEmpty()) {
            return ElResult.error("productIds 不能为空");
        }
        productService.removeByIds(productIds);
        return ElResult.ok();
    }
}