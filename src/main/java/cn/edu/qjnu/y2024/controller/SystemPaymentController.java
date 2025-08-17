package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.SystemPayments;
import cn.edu.qjnu.y2024.service.ISystemPaymentsService;
import cn.edu.qjnu.y2024.util.ElResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/systemPayment")
@CrossOrigin
public class SystemPaymentController {
    private static final Logger logger = LoggerFactory.getLogger(SystemPaymentController.class);

    @Resource
    private ISystemPaymentsService systemPaymentService;

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
        addSecurityHeaders(response);
        Page<SystemPayments> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SystemPayments> queryWrapper = new QueryWrapper<>();
        Page<SystemPayments> systemPaymentPage = systemPaymentService.page(page, queryWrapper);
        return ElResult.ok(systemPaymentPage.getTotal(), systemPaymentPage.getRecords());
    }

    /**
     * 新增或更新
     * @param systemPayment SystemPayments对象
     * @return ElResult对象
     */
    @PostMapping("/add")
    public ElResult save(@RequestBody SystemPayments systemPayment) {
        if (systemPayment.getPaymentMethod() == null || systemPayment.getPaymentMethod().isEmpty()) {
            return ElResult.error("支付方式不能为空");
        }
        if (systemPayment.getAccountInfo() == null || systemPayment.getAccountInfo().isEmpty()) {
            return ElResult.error("账户信息不能为空");
        }
        // 自动生成 paymentId 如果为空
        if (systemPayment.getPaymentId() == null || systemPayment.getPaymentId().isEmpty()) {
            systemPayment.setPaymentId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10)); // 生成10位唯一ID
        }
        try {
            boolean success = systemPaymentService.saveOrUpdate(systemPayment);
            return success ? ElResult.ok() : ElResult.error("新增支付信息失败");
        } catch (Exception e) {
            logger.error("新增失败: {}", e.getMessage(), e);
            return ElResult.error("新增失败：" + e.getMessage());
        }
    }

    /**
     * 删除
     * @param systemPayment SystemPayments对象
     * @return ElResult对象
     */
    @PostMapping("/delete")
    public ElResult delete(@RequestBody SystemPayments systemPayment) {
        if (systemPayment.getPaymentId() == null || systemPayment.getPaymentId().isEmpty()) {
            return ElResult.error("支付ID不能为空");
        }
        boolean success = systemPaymentService.removeById(systemPayment.getPaymentId());
        return success ? ElResult.ok() : ElResult.error("删除支付信息失败");
    }

    /**
     * 更新
     * @param systemPayment SystemPayments对象
     * @return ElResult对象
     */
    @PostMapping("/update")
    public ElResult update(@RequestBody SystemPayments systemPayment) {
        if (systemPayment.getPaymentId() == null || systemPayment.getPaymentId().isEmpty()) {
            return ElResult.error("支付ID不能为空");
        }
        try {
            boolean success = systemPaymentService.updateById(systemPayment);
            return success ? ElResult.ok() : ElResult.error("更新支付信息失败");
        } catch (Exception e) {
            logger.error("更新失败: {}", e.getMessage(), e);
            return ElResult.error("更新失败：" + e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param paymentIds 支付ID列表
     * @return ElResult对象
     */
    @PostMapping("/batchDelete")
    public ElResult batchDelete(@RequestBody List<String> paymentIds) {
        if (paymentIds == null || paymentIds.isEmpty()) {
            return ElResult.error("支付ID列表不能为空");
        }
        try {
            boolean success = systemPaymentService.removeByIds(paymentIds);
            return success ? ElResult.ok() : ElResult.error("批量删除支付信息失败");
        } catch (Exception e) {
            logger.error("批量删除失败: {}", e.getMessage(), e);
            return ElResult.error("批量删除失败：" + e.getMessage());
        }
    }
}