package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.CustomerAddresses;
import cn.edu.qjnu.y2024.service.ICustomerAddressesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.edu.qjnu.y2024.util.ElResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/customerAddress")
@CrossOrigin
public class CustomerAddressController {

    @Resource
    private ICustomerAddressesService customerAddressService;

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
        Page<CustomerAddresses> page = new Page<>(pageNum, pageSize);
        QueryWrapper<CustomerAddresses> queryWrapper = new QueryWrapper<>();
        Page<CustomerAddresses> customerAddressPage = customerAddressService.page(page, queryWrapper);
        return ElResult.ok(customerAddressPage.getTotal(), customerAddressPage.getRecords());
    }

    /**
     * 新增或更新
     * @param customerAddress CustomerAddresses对象
     * @return ElResult对象
     */
    @PostMapping("/add")
    public ElResult save(@RequestBody CustomerAddresses customerAddress) {
        customerAddressService.saveOrUpdate(customerAddress);
        return ElResult.ok();
    }

    /**
     * 删除
     * @param customerAddress CustomerAddresses对象
     * @return ElResult对象
     */
    @PostMapping("/delete")
    public ElResult delete(@RequestBody CustomerAddresses customerAddress) {
        customerAddressService.removeById(customerAddress.getAddressId());
        return ElResult.ok();
    }

    /**
     * 更新
     * @param customerAddress CustomerAddresses对象
     * @return ElResult对象
     */
    @PostMapping("/update")
    public ElResult update(@RequestBody CustomerAddresses customerAddress) {
        customerAddressService.updateById(customerAddress);
        return ElResult.ok();
    }

    /**
     * 批量删除
     * @param addressIds 地址ID列表
     * @return ElResult对象
     */
    @PostMapping("/batchDelete")
    public ElResult batchDelete(@RequestBody List<String> addressIds) {
        customerAddressService.removeByIds(addressIds);
        return ElResult.ok();
    }
}

