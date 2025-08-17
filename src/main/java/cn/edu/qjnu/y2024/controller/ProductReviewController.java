package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.ProductReviews;
import cn.edu.qjnu.y2024.service.IProductReviewsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.edu.qjnu.y2024.util.ElResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/productReview")
@CrossOrigin(origins = "http://localhost:5173") // 明确指定前端来源
public class ProductReviewController {

    @Resource
    private IProductReviewsService productReviewService;

    // 添加安全头
    private void addSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
    }

    /**
     * 分页查询
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return ElResult对象，包含分页数据
     */
    @GetMapping("/page")
    public ElResult findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             HttpServletResponse response) {
        try {
            addSecurityHeaders(response);
            Page<ProductReviews> page = new Page<>(pageNum, pageSize);
            QueryWrapper<ProductReviews> queryWrapper = new QueryWrapper<>();
            Page<ProductReviews> productReviewPage = productReviewService.page(page, queryWrapper);
            return ElResult.ok(productReviewPage.getTotal(), productReviewPage.getRecords());
        } catch (Exception e) {
            return ElResult.error("500 - 服务器内部错误: " + e.getMessage());
        }
    }

    /**
     * 新增
     * @param productReview ProductReviews对象
     * @return ElResult对象
     */
    @PostMapping("/add")
    public ElResult add(@RequestBody ProductReviews productReview) {
        try {
            // 设置创建时间和更新时间
            LocalDateTime now = LocalDateTime.now();
            productReview.setCreatedAt(now);
            productReview.setUpdatedAt(now);

            productReviewService.saveOrUpdate(productReview);
            return ElResult.ok();
        } catch (Exception e) {
            return ElResult.error("500 - 新增失败: " + e.getMessage());
        }
    }

    /**
     * 更新
     * @param productReview ProductReviews对象
     * @return ElResult对象
     */
    @PostMapping("/update")
    public ElResult update(@RequestBody ProductReviews productReview) {
        try {
            productReviewService.updateById(productReview);
            return ElResult.ok();
        } catch (Exception e) {
            return ElResult.error("500 - 更新失败: " + e.getMessage());
        }
    }

    /**
     * 删除
     * @param productReview ProductReviews对象
     * @return ElResult对象
     */
    @PostMapping("/delete")
    public ElResult delete(@RequestBody ProductReviews productReview) {
        try {
            productReviewService.removeById(productReview.getReviewId());
            return ElResult.ok();
        } catch (Exception e) {
            return ElResult.error("500 - 删除失败: " + e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param reviewIds 评价ID列表
     * @return ElResult对象
     */
    @PostMapping("/batchDelete")
    public ElResult batchDelete(@RequestBody List<String> reviewIds) {
        try {
            productReviewService.removeByIds(reviewIds);
            return ElResult.ok();
        } catch (Exception e) {
            return ElResult.error("500 - 批量删除失败: " + e.getMessage());
        }
    }
}