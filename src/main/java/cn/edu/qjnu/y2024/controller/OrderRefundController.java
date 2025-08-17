package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.OrderRefunds;
import cn.edu.qjnu.y2024.service.IOrderRefundsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.edu.qjnu.y2024.util.ElResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderRefund")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class OrderRefundController {

    @Resource
    private IOrderRefundsService orderRefundService;

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
        Page<OrderRefunds> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OrderRefunds> queryWrapper = new QueryWrapper<>();
        Page<OrderRefunds> orderRefundPage = orderRefundService.page(page, queryWrapper);
        return ElResult.ok(orderRefundPage.getTotal(), orderRefundPage.getRecords());
    }

    /**
     * 新增或更新
     * @param orderRefund OrderRefunds对象
     * @return ElResult对象
     */
    @PostMapping("/add")
    public ElResult save(@RequestBody OrderRefunds orderRefund) {
        orderRefundService.saveOrUpdate(orderRefund);
        return ElResult.ok();
    }

    /**
     * 删除
     * @param orderRefund OrderRefunds对象
     * @return ElResult对象
     */
    @PostMapping("/delete")
    public ElResult delete(@RequestBody OrderRefunds orderRefund) {
        orderRefundService.removeById(orderRefund.getRefundId());
        return ElResult.ok();
    }

    /**
     * 更新
     * @param orderRefund OrderRefunds对象
     * @return ElResult对象
     */
    @PostMapping("/update")
    public ElResult update(@RequestBody OrderRefunds orderRefund) {
        orderRefundService.updateById(orderRefund);
        return ElResult.ok();
    }

    /**
     * 批量删除
     * @param body 包含待删除ID列表的Map
     * @return ElResult对象
     */
    @Transactional
    @PostMapping("/batchDelete")
    public ElResult batchDelete(@RequestBody Map<String, List<String>> body) {
        // 将此处获取键名的 "ids" 修改为 "refundIds"
        List<String> ids = body.get("refundIds");

        if (ids == null || ids.isEmpty()) {
            return ElResult.error("ids 不能为空");
        }

        orderRefundService.removeByIds(ids);

        return ElResult.ok();
    }
}


