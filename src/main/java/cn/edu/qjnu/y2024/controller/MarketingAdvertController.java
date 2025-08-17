package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.MarketingAdverts;
import cn.edu.qjnu.y2024.service.IMarketingAdvertsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.edu.qjnu.y2024.util.ElResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/marketingAdvert")
@CrossOrigin
public class MarketingAdvertController {

    @Resource
    private IMarketingAdvertsService marketingAdvertService;

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
        Page<MarketingAdverts> page = new Page<>(pageNum, pageSize);
        QueryWrapper<MarketingAdverts> queryWrapper = new QueryWrapper<>();
        Page<MarketingAdverts> marketingAdvertPage = marketingAdvertService.page(page, queryWrapper);
        return ElResult.ok(marketingAdvertPage.getTotal(), marketingAdvertPage.getRecords());
    }

    /**
     * 新增或更新
     * @param marketingAdvert MarketingAdverts对象
     * @return ElResult对象
     */
    @PostMapping("/add")
    public ElResult save(@RequestBody MarketingAdverts marketingAdvert) {
        marketingAdvertService.saveOrUpdate(marketingAdvert);
        return ElResult.ok();
    }

    /**
     * 删除
     * @param marketingAdvert MarketingAdverts对象
     * @return ElResult对象
     */
    @PostMapping("/delete")
    public ElResult delete(@RequestBody MarketingAdverts marketingAdvert) {
        marketingAdvertService.removeById(marketingAdvert.getAdvertId());
        return ElResult.ok();
    }

    /**
     * 更新
     * @param marketingAdvert MarketingAdverts对象
     * @return ElResult对象
     */
    @PostMapping("/update")
    public ElResult update(@RequestBody MarketingAdverts marketingAdvert) {
        marketingAdvertService.updateById(marketingAdvert);
        return ElResult.ok();
    }

    /**
     * 批量删除
     * @param advertIds 广告ID列表
     * @return ElResult对象
     */
    @PostMapping("/batchDelete")
    public ElResult batchDelete(@RequestBody List<String> advertIds) {
        marketingAdvertService.removeByIds(advertIds);
        return ElResult.ok();
    }
}