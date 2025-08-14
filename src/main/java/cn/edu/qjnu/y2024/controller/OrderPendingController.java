package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.OrderPending;
import cn.edu.qjnu.y2024.service.IOrderPendingService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.edu.qjnu.y2024.util.ElResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 待处理订单前端控制器
 * </p>
 *
 * @author y2024
 * @since 2025-08-14
 */
@RestController
@RequestMapping("/orderPending")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderPendingController {

    @Resource
    private IOrderPendingService orderPendingService;

    // 添加安全头
    private void addSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
    }

    /**
     * 分页查询待处理订单
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return ElResult对象，包含分页数据
     */
    @GetMapping("/pendingPage")
    public ElResult findPendingPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    HttpServletResponse response) {
        try {
            addSecurityHeaders(response);
            Page<OrderPending> page = new Page<>(pageNum, pageSize);
            QueryWrapper<OrderPending> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("status", "pending_payment", "pending_shipment");
            Page<OrderPending> orderPage = orderPendingService.page(page, queryWrapper);
            return ElResult.ok(orderPage.getTotal(), orderPage.getRecords());
        } catch (Exception e) {
            return ElResult.error("500 - 服务器内部错误: " + e.getMessage());
        }
    }

    /**
     * 新增待处理订单
     * @param orderPending OrderPending对象
     * @return ElResult对象
     */
    @PostMapping("/addPending")
    public ElResult addPending(@RequestBody OrderPending orderPending) {
        try {
            orderPendingService.save(orderPending);
            return ElResult.ok();
        } catch (Exception e) {
            return ElResult.error("500 - 新增失败: " + e.getMessage());
        }
    }

    /**
     * 更新待处理订单
     * @param orderPending OrderPending对象
     * @return ElResult对象
     */
    @PostMapping("/updatePending")
    public ElResult updatePending(@RequestBody OrderPending orderPending) {
        try {
            orderPendingService.updateById(orderPending);
            return ElResult.ok();
        } catch (Exception e) {
            return ElResult.error("500 - 更新失败: " + e.getMessage());
        }
    }

    /**
     * 删除待处理订单
     * @param orderPending OrderPending对象
     * @return ElResult对象
     */
    @PostMapping("/deletePending")
    public ElResult deletePending(@RequestBody OrderPending orderPending) {
        try {
            orderPendingService.removeById(orderPending.getOrderId());
            return ElResult.ok();
        } catch (Exception e) {
            return ElResult.error("500 - 删除失败: " + e.getMessage());
        }
    }

    /**
     * 批量删除待处理订单
     * @param orderIds 订单ID列表
     * @return ElResult对象
     */
    @PostMapping("/batchDeletePending")
    public ElResult batchDeletePending(@RequestBody List<String> orderIds) {
        try {
            orderPendingService.removeByIds(orderIds);
            return ElResult.ok();
        } catch (Exception e) {
            return ElResult.error("500 - 批量删除失败: " + e.getMessage());
        }
    }
}