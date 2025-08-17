package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.Customers;
import cn.edu.qjnu.y2024.service.ICustomersService;
import cn.edu.qjnu.y2024.util.ElResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomersController {

    private final ICustomersService customersService;

    @Autowired
    public CustomersController(ICustomersService customersService) {
        this.customersService = customersService;
    }

    // 添加安全头
    private void addSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
    }

    /**
     * 前台用户登录
     */
    @PostMapping("/login")
    public ElResult<Customers> login(@RequestBody Map<String, String> credentials, HttpServletResponse response) {
        addSecurityHeaders(response);

        try {
            String phone = credentials.get("phone");
            String password = credentials.get("password");

            if (phone == null || password == null || phone.trim().isEmpty() || password.trim().isEmpty()) {
                return ElResult.error("手机号或密码不能为空");
            }

            QueryWrapper<Customers> qw = new QueryWrapper<>();
            qw.eq("customer_phone", phone.trim());
            Customers customer = customersService.getOne(qw);

            if (customer == null) {
                return ElResult.error("用户不存在");
            }

            String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
            if (!encryptedPassword.equals(customer.getPassword())) {
                return ElResult.error("密码错误");
            }

            // 更新最后登录时间
            customer.setLastLoginTime(LocalDateTime.now());
            customer.setUpdatedAt(LocalDateTime.now()); // 手动设置更新时间
            customersService.updateById(customer);

            return ElResult.ok(1L, Collections.singletonList(customer));
        } catch (Exception e) {
            e.printStackTrace();
            return ElResult.error("登录失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID获取客户详情
     */
    @GetMapping("/detail/{id}")
    public ElResult<Customers> getCustomerDetail(@PathVariable Integer id, HttpServletResponse response) {
        addSecurityHeaders(response);
        try {
            if (id == null) {
                return ElResult.error("客户ID不能为空");
            }
            Customers customer = customersService.getById(id);
            if (customer != null) {
                return ElResult.ok(1L, Collections.singletonList(customer));
            } else {
                return ElResult.error("客户不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ElResult.error("查询客户详情失败：" + e.getMessage());
        }
    }

    // 获取客户列表（全部）
    @GetMapping("/listCustomers")
    public ElResult<Customers> listCustomers(HttpServletResponse response) {
        addSecurityHeaders(response);
        try {
            List<Customers> list = customersService.list();
            return ElResult.ok((long) list.size(), list);
        } catch (Exception e) {
            e.printStackTrace();
            return ElResult.error("获取客户列表失败：" + e.getMessage());
        }
    }

    // 分页查询客户
    @GetMapping("/pageCustomers")
    public ElResult<Customers> pageCustomers(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             HttpServletResponse response) {
        addSecurityHeaders(response);
        try {
            if (pageNum <= 0) pageNum = 1;
            if (pageSize <= 0) pageSize = 10;

            Page<Customers> page = new Page<>(pageNum, pageSize);
            Page<Customers> customerPage = customersService.page(page);
            return ElResult.ok(customerPage.getTotal(), customerPage.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
            return ElResult.error("分页查询客户失败：" + e.getMessage());
        }
    }

    // 新增客户（注册）
    @PostMapping("/add")
    public ElResult<Void> addCustomer(@RequestBody Customers customer, HttpServletResponse response) {
        addSecurityHeaders(response);

        try {
            // 基础参数验证
            if (customer == null) {
                return ElResult.error("客户信息不能为空");
            }

            if (customer.getCustomerName() == null || customer.getCustomerName().trim().isEmpty()) {
                return ElResult.error("用户名不能为空");
            }
            if (customer.getCustomerPhone() == null || customer.getCustomerPhone().trim().isEmpty()) {
                return ElResult.error("手机号不能为空");
            }
            if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
                return ElResult.error("密码不能为空");
            }

            // 清理输入数据
            customer.setCustomerName(customer.getCustomerName().trim());
            customer.setCustomerPhone(customer.getCustomerPhone().trim());

            // 邮箱验证（如果提供）
            if (customer.getCustomerEmail() != null && !customer.getCustomerEmail().trim().isEmpty()) {
                customer.setCustomerEmail(customer.getCustomerEmail().trim());
                String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
                if (!customer.getCustomerEmail().matches(emailPattern)) {
                    return ElResult.error("请输入正确的邮箱格式");
                }

                // 检查邮箱是否已存在
                QueryWrapper<Customers> emailQuery = new QueryWrapper<>();
                emailQuery.eq("customer_email", customer.getCustomerEmail());
                if (customersService.count(emailQuery) > 0) {
                    return ElResult.error("该邮箱已被注册");
                }
            }

            // 手机号格式验证
            String phonePattern = "^1[3-9]\\d{9}$";
            if (!customer.getCustomerPhone().matches(phonePattern)) {
                return ElResult.error("请输入正确的手机号格式");
            }

            // 检查手机号是否已存在
            QueryWrapper<Customers> phoneQuery = new QueryWrapper<>();
            phoneQuery.eq("customer_phone", customer.getCustomerPhone());
            if (customersService.count(phoneQuery) > 0) {
                return ElResult.error("该手机号已被注册");
            }

            // 密码长度验证
            if (customer.getPassword().length() < 6 || customer.getPassword().length() > 20) {
                return ElResult.error("密码长度必须在6-20位之间");
            }

            // 用户名长度验证
            if (customer.getCustomerName().length() < 2 || customer.getCustomerName().length() > 20) {
                return ElResult.error("用户名长度必须在2-20位之间");
            }

            // 密码加密
            String encryptedPassword = DigestUtils.md5DigestAsHex(customer.getPassword().getBytes(StandardCharsets.UTF_8));
            customer.setPassword(encryptedPassword);

            // 设置默认值
            if (customer.getCustomerLevel() == null || customer.getCustomerLevel().trim().isEmpty()) {
                customer.setCustomerLevel("普通");
            }
            if (customer.getStatus() == null || customer.getStatus().trim().isEmpty()) {
                customer.setStatus("正常");
            }
            if (customer.getTotalOrders() == null) {
                customer.setTotalOrders(0);
            }
            if (customer.getTotalAmount() == null) {
                customer.setTotalAmount(new BigDecimal("0.00"));
            }

            // 手动设置时间字段 - 关键修复
            LocalDateTime now = LocalDateTime.now();
            customer.setRegistrationDate(now);
            customer.setCreatedAt(now);      // 手动设置创建时间
            customer.setUpdatedAt(now);      // 手动设置更新时间

            // 保存到数据库
            boolean success = customersService.save(customer);
            if (success) {
                return ElResult.ok();
            } else {
                return ElResult.error("注册失败，请稍后重试");
            }

        } catch (org.springframework.dao.DuplicateKeyException e) {
            // 唯一键冲突异常
            return ElResult.error("手机号已被注册");
        } catch (Exception e) {
            e.printStackTrace();
            return ElResult.error("注册失败：" + e.getMessage());
        }
    }

    // 修改客户
    @PostMapping("/update")
    public ElResult<Void> updateCustomer(@RequestBody Customers customer, HttpServletResponse response) {
        addSecurityHeaders(response);

        try {
            if (customer == null || customer.getCustomerId() == null) {
                return ElResult.error("客户ID不能为空");
            }

            // 检查客户是否存在
            Customers existingCustomer = customersService.getById(customer.getCustomerId());
            if (existingCustomer == null) {
                return ElResult.error("客户不存在");
            }

            // 如果要修改密码，进行加密
            if (customer.getPassword() != null && !customer.getPassword().trim().isEmpty()) {
                if (customer.getPassword().length() < 6 || customer.getPassword().length() > 20) {
                    return ElResult.error("密码长度必须在6-20位之间");
                }
                String encryptedPassword = DigestUtils.md5DigestAsHex(customer.getPassword().getBytes(StandardCharsets.UTF_8));
                customer.setPassword(encryptedPassword);
            } else {
                // 如果密码为空，保持原密码不变
                customer.setPassword(null);
            }

            // 手动设置更新时间
            customer.setUpdatedAt(LocalDateTime.now());

            boolean success = customersService.updateById(customer);
            if (success) {
                return ElResult.ok();
            } else {
                return ElResult.error("更新失败，请稍后重试");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ElResult.error("更新客户信息失败：" + e.getMessage());
        }
    }

    // 删除客户
    @PostMapping("/delete")
    public ElResult<Void> deleteCustomer(@RequestBody Customers customer, HttpServletResponse response) {
        addSecurityHeaders(response);

        try {
            if (customer == null || customer.getCustomerId() == null) {
                return ElResult.error("客户ID不能为空");
            }

            boolean success = customersService.removeById(customer.getCustomerId());
            if (success) {
                return ElResult.ok();
            } else {
                return ElResult.error("删除失败，客户不存在");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ElResult.error("删除客户失败：" + e.getMessage());
        }
    }

    // 批量删除客户
    @PostMapping("/batchDelete")
    public ElResult<Void> batchDeleteCustomers(@RequestBody List<Integer> customerIds, HttpServletResponse response) {
        addSecurityHeaders(response);

        try {
            if (customerIds == null || customerIds.isEmpty()) {
                return ElResult.error("客户ID列表不能为空");
            }

            boolean success = customersService.removeByIds(customerIds);
            if (success) {
                return ElResult.ok();
            } else {
                return ElResult.error("批量删除失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ElResult.error("批量删除客户失败：" + e.getMessage());
        }
    }
}