package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.SystemPayments;
import cn.edu.qjnu.y2024.service.ISystemPaymentsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.edu.qjnu.y2024.util.ElResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/systemPayment")
@CrossOrigin
public class SystemPaymentController {

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
        systemPaymentService.saveOrUpdate(systemPayment);
        return ElResult.ok();
    }

    /**
     * 删除
     * @param systemPayment SystemPayments对象
     * @return ElResult对象
     */
    @PostMapping("/delete")
    public ElResult delete(@RequestBody SystemPayments systemPayment) {
        systemPaymentService.removeById(systemPayment.getPaymentId());
        return ElResult.ok();
    }

    /**
     * 更新
     * @param systemPayment SystemPayments对象
     * @return ElResult对象
     */
    @PostMapping("/update")
    public ElResult update(@RequestBody SystemPayments systemPayment) {
        systemPaymentService.updateById(systemPayment);
        return ElResult.ok();
    }
}


