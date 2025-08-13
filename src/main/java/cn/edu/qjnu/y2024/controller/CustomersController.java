package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.Customers;
import cn.edu.qjnu.y2024.service.ICustomersService;
import cn.edu.qjnu.y2024.util.ElResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomersController {

    private final ICustomersService customersService;

    @Autowired
    public CustomersController(ICustomersService customersService) {
        this.customersService = customersService;
    }

    // 添加CSP头
    private void addSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "no sniff");
        response.setHeader("X-Frame-Options", "DENY");
    }

    // 获取客户列表（全部）
    @GetMapping("/listCustomers")
    public ElResult<Customers> listCustomers(HttpServletResponse response) {
        addSecurityHeaders(response);
        try {
            return ElResult.ok(0L, customersService.list());
        } catch (Exception e) {
            return ElResult.error("获取客户列表失败：" + e.getMessage());
        }
    }

    // 分页查询客户
    @GetMapping("/pageCustomers")
    public ElResult<Customers> pageCustomers(Integer pageNum, Integer pageSize, HttpServletResponse response) {
        addSecurityHeaders(response);
        try {
            if (pageNum == null || pageNum <= 0) pageNum = 1;
            if (pageSize == null || pageSize <= 0) pageSize = 10;

            Page<Customers> page = new Page<>(pageNum, pageSize);
            Page<Customers> customerPage = customersService.page(page);
            return ElResult.ok(customerPage.getTotal(), customerPage.getRecords());
        } catch (Exception e) {
            return ElResult.error("分页查询失败：" + e.getMessage());
        }
    }

    // 新增客户
    @PostMapping("/add")
    public ElResult<Customers> addCustomer(@RequestBody Customers customer, HttpServletResponse response) {
        addSecurityHeaders(response);
        try {
            if (customer.getCustomerName() == null || customer.getCustomerName().trim().isEmpty()) {
                return ElResult.error("客户名称不能为空");
            }
            if (customer.getCreatedAt() == null || customer.getCreatedAt().trim().isEmpty()) {
                return ElResult.error("创建时间不能为空");
            }

            QueryWrapper<Customers> qw = new QueryWrapper<>();
            qw.eq("customerName", customer.getCustomerName());
            if (customersService.count(qw) > 0) {
                return ElResult.error("客户名称已存在");
            }

            boolean success = customersService.save(customer);
            return success ? ElResult.ok() : ElResult.error("新增客户失败");
        } catch (Exception e) {
            return ElResult.error("新增失败：" + e.getMessage());
        }
    }

    // 修改客户
    @PostMapping("/update")
    public ElResult<Customers> updateCustomer(@RequestBody Customers customer, HttpServletResponse response) {
        addSecurityHeaders(response);
        try {
            if (customer.getCustomerId() == null) {
                return ElResult.error("客户ID不能为空");
            }

            boolean success = customersService.updateById(customer);
            return success ? ElResult.ok() : ElResult.error("修改客户失败");
        } catch (Exception e) {
            return ElResult.error("修改失败：" + e.getMessage());
        }
    }

    // 删除客户
    @PostMapping("/delete")
    public ElResult<Customers> deleteCustomer(@RequestBody Customers customer, HttpServletResponse response) {
        addSecurityHeaders(response);
        try {
            if (customer.getCustomerId() == null) {
                return ElResult.error("客户ID不能为空");
            }
            boolean success = customersService.removeById(customer.getCustomerId());
            return success ? ElResult.ok() : ElResult.error("删除客户失败");
        } catch (Exception e) {
            return ElResult.error("删除失败：" + e.getMessage());
        }
    }
}