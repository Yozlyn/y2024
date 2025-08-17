package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.MarketingActivities;
import cn.edu.qjnu.y2024.service.IMarketingActivitiesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.edu.qjnu.y2024.util.ElResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/marketingActivity")
@CrossOrigin
public class MarketingActivityController {

    @Resource
    private IMarketingActivitiesService marketingActivityService;

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
        Page<MarketingActivities> page = new Page<>(pageNum, pageSize);
        QueryWrapper<MarketingActivities> queryWrapper = new QueryWrapper<>();
        Page<MarketingActivities> marketingActivityPage = marketingActivityService.page(page, queryWrapper);
        return ElResult.ok(marketingActivityPage.getTotal(), marketingActivityPage.getRecords());
    }

    /**
     * 新增或更新
     * @param marketingActivity MarketingActivities对象
     * @return ElResult对象
     */
    @PostMapping("/add")
    public ElResult save(@RequestBody MarketingActivities marketingActivity) {
        marketingActivityService.saveOrUpdate(marketingActivity);
        return ElResult.ok();
    }

    /**
     * 删除
     * @param marketingActivity MarketingActivities对象
     * @return ElResult对象
     */
    @PostMapping("/delete")
    public ElResult delete(@RequestBody MarketingActivities marketingActivity) {
        marketingActivityService.removeById(marketingActivity.getActivityId());
        return ElResult.ok();
    }

    /**
     * 更新
     * @param marketingActivity MarketingActivities对象
     * @return ElResult对象
     */
    @PostMapping("/update")
    public ElResult update(@RequestBody MarketingActivities marketingActivity) {
        marketingActivityService.updateById(marketingActivity);
        return ElResult.ok();
    }

    /**
     * 批量删除
     * @param activityIds 活动ID列表
     * @return ElResult对象
     */
    @PostMapping("/batchDelete")
    public ElResult batchDelete(@RequestBody List<String> activityIds) {
        marketingActivityService.removeByIds(activityIds);
        return ElResult.ok();
    }
}