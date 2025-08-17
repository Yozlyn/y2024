package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.OrderStatistics;
import cn.edu.qjnu.y2024.service.IOrderStatisticsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.edu.qjnu.y2024.util.ElResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/orderStatistic")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderStatisticController {

    @Resource
    private IOrderStatisticsService orderStatisticService;

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
        Page<OrderStatistics> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OrderStatistics> queryWrapper = new QueryWrapper<>();
        Page<OrderStatistics> orderStatisticPage = orderStatisticService.page(page, queryWrapper);
        return ElResult.ok(orderStatisticPage.getTotal(), orderStatisticPage.getRecords());
    }

    /**
     * 新增或更新
     * @param orderStatistic OrderStatistics对象
     * @return ElResult对象
     */
    @PostMapping("/add")
    public ElResult save(@RequestBody OrderStatistics orderStatistic) {
        orderStatisticService.saveOrUpdate(orderStatistic);
        return ElResult.ok();
    }

    /**
     * 删除
     * @param orderStatistic OrderStatistics对象
     * @return ElResult对象
     */
    @PostMapping("/delete")
    public ElResult delete(@RequestBody OrderStatistics orderStatistic) {
        orderStatisticService.removeById(orderStatistic.getOrderNo());
        return ElResult.ok();
    }

    /**
     * 更新
     * @param orderStatistic OrderStatistics对象
     * @return ElResult对象
     */
    @PostMapping("/update")
    public ElResult update(@RequestBody OrderStatistics orderStatistic) {
        orderStatisticService.updateById(orderStatistic);
        return ElResult.ok();
    }
}


