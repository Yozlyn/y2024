package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.MarketingCoupons;
import cn.edu.qjnu.y2024.service.IMarketingCouponsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.edu.qjnu.y2024.util.ElResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/marketingCoupon")
@CrossOrigin
public class MarketingCouponController {

    @Resource
    private IMarketingCouponsService marketingCouponService;

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
        Page<MarketingCoupons> page = new Page<>(pageNum, pageSize);
        QueryWrapper<MarketingCoupons> queryWrapper = new QueryWrapper<>();
        Page<MarketingCoupons> marketingCouponPage = marketingCouponService.page(page, queryWrapper);
        return ElResult.ok(marketingCouponPage.getTotal(), marketingCouponPage.getRecords());
    }

    /**
     * 新增或更新
     * @param marketingCoupon MarketingCoupons对象
     * @return ElResult对象
     */
    @PostMapping("/add")
    public ElResult save(@RequestBody MarketingCoupons marketingCoupon) {
        marketingCouponService.saveOrUpdate(marketingCoupon);
        return ElResult.ok();
    }

    /**
     * 删除
     * @param marketingCoupon MarketingCoupons对象
     * @return ElResult对象
     */
    @PostMapping("/delete")
    public ElResult delete(@RequestBody MarketingCoupons marketingCoupon) {
        marketingCouponService.removeById(marketingCoupon.getCouponId());
        return ElResult.ok();
    }

    /**
     * 更新
     * @param marketingCoupon MarketingCoupons对象
     * @return ElResult对象
     */
    @PostMapping("/update")
    public ElResult update(@RequestBody MarketingCoupons marketingCoupon) {
        marketingCouponService.updateById(marketingCoupon);
        return ElResult.ok();
    }

    /**
     * 批量删除
     * @param couponIds 优惠券ID列表
     * @return ElResult对象
     */
    @PostMapping("/batchDelete")
    public ElResult batchDelete(@RequestBody List<String> couponIds) {
        marketingCouponService.removeByIds(couponIds);
        return ElResult.ok();
    }
}
