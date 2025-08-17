package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.OrderList;
import cn.edu.qjnu.y2024.service.IOrderListService;
import cn.edu.qjnu.y2024.util.ElResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderList")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class OrderListController {

    @Resource
    private IOrderListService orderListService;

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

        Page<OrderList> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OrderList> queryWrapper = new QueryWrapper<>();
        Page<OrderList> orderListPage = orderListService.page(page, queryWrapper);
        return ElResult.ok(orderListPage.getTotal(), orderListPage.getRecords());
    }

    // 新增
    @PostMapping("/add")
    public ElResult add(@RequestBody OrderList orderList) {
        orderListService.save(orderList);
        return ElResult.ok();
    }

    // 更新
    @PostMapping("/update")
    public ElResult update(@RequestBody OrderList orderList) {
        orderListService.updateById(orderList);
        return ElResult.ok();
    }

    // 删除单个
    @PostMapping("/delete")
    public ElResult delete(@RequestBody OrderList orderList) {
        orderListService.removeById(orderList.getOrderId());
        return ElResult.ok();
    }

    // 批量删除
    @PostMapping("/batchDelete")
    public ElResult batchDelete(@RequestBody Map<String, List<String>> body) {
        List<String> orderIds = body.get("orderIds");
        if (orderIds == null || orderIds.isEmpty()) {
            return ElResult.error("orderIds 不能为空");
        }
        orderListService.removeByIds(orderIds);
        return ElResult.ok();
    }
}