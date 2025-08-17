package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.SysUser;
import cn.edu.qjnu.y2024.service.ISysUserService;
import cn.edu.qjnu.y2024.util.ElResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/sysUser")
@CrossOrigin
public class SysUserController {
    private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);
    private final ISysUserService sysUserService;

    @Autowired
    public SysUserController(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    private void addSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "no sniff");
        response.setHeader("X-Frame-Options", "DENY");
    }

    // --- 新增：用户注册功能 ---
    @PostMapping("/register")
    public ElResult<SysUser> registerUser(@RequestBody SysUser sysUser, HttpServletResponse response) {
        addSecurityHeaders(response);
        try {
            // 1. 基本数据校验
            if (sysUser.getSuName() == null || sysUser.getSuName().trim().isEmpty()) {
                return ElResult.error("用户名不能为空");
            }
            if (sysUser.getSuPwd() == null || sysUser.getSuPwd().trim().isEmpty()) {
                return ElResult.error("密码不能为空");
            }
            if (sysUser.getEmail() == null || sysUser.getEmail().trim().isEmpty()) {
                return ElResult.error("邮箱不能为空");
            }

            // 2. 检查用户名是否已存在
            QueryWrapper<SysUser> nameQuery = new QueryWrapper<>();
            nameQuery.eq("su_name", sysUser.getSuName());
            if (sysUserService.count(nameQuery) > 0) {
                return ElResult.error("该用户名已被注册");
            }

            // 3. 检查邮箱是否已存在
            QueryWrapper<SysUser> emailQuery = new QueryWrapper<>();
            emailQuery.eq("email", sysUser.getEmail());
            if (sysUserService.count(emailQuery) > 0) {
                return ElResult.error("该邮箱已被注册");
            }

            // 4. 对密码进行MD5加密
            sysUser.setSuPwd(DigestUtils.md5DigestAsHex(
                    sysUser.getSuPwd().getBytes(StandardCharsets.UTF_8)));

            // 5. 设置新用户的默认值
            sysUser.setSuRole("user"); // 默认角色
            sysUser.setStatus("active"); // 默认状态
            sysUser.setSuTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            // 6. 保存用户到数据库
            boolean success = sysUserService.save(sysUser);
            return success ? ElResult.ok("注册成功！") : ElResult.error("注册失败，请稍后重试");
        } catch (Exception e) {
            logger.error("注册用户时发生错误: {}", e.getMessage(), e);
            return ElResult.error("服务器内部错误，注册失败");
        }
    }


    // 获取用户列表 (完整方法)
    @GetMapping("/listUser")
    public ElResult<SysUser> listUser(HttpServletResponse response) {
        addSecurityHeaders(response);
        List<SysUser> users = sysUserService.list();
        return ElResult.ok((long) users.size(), users);
    }

    // 用户登录 (完整方法)
    @PostMapping("/login")
    public ElResult<SysUser> login(@RequestBody SysUser sysUser, HttpServletResponse response) {
        addSecurityHeaders(response);
        // 对传入的密码进行加密，以便与数据库中的密码进行比对
        sysUser.setSuPwd(DigestUtils.md5DigestAsHex(
                sysUser.getSuPwd().getBytes(StandardCharsets.UTF_8)));
        QueryWrapper<SysUser> qw = new QueryWrapper<>(sysUser);
        List<SysUser> users = sysUserService.list(qw);
        if (users.size() == 1) {
            // 登录成功，返回用户信息
            return ElResult.ok(1L, users);
        } else {
            return ElResult.error("用户名或密码错误");
        }
    }

    // 分页查询用户 (修复返回结构以匹配前端)
    @GetMapping("/pageUser") // 使用GET请求更符合RESTful规范
    public ElResult<SysUser> pageUser(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            HttpServletResponse response) {
        addSecurityHeaders(response);
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        Page<SysUser> userPage = sysUserService.page(page);
        // 直接使用ElResult的ok方法，假设它能生成 {code: 0, count: total, data: records} 的结构
        return ElResult.ok(userPage.getTotal(), userPage.getRecords());
    }

    // 新增用户 (简化时间处理)
    @PostMapping("/add")
    public ElResult<SysUser> addUser(@RequestBody SysUser sysUser, HttpServletResponse response) {
        addSecurityHeaders(response);
        try {
            if (sysUser.getSuName() == null || sysUser.getSuName().trim().isEmpty()) {
                return ElResult.error("用户名不能为空");
            }
            if (sysUser.getSuPwd() == null || sysUser.getSuPwd().trim().isEmpty()) {
                return ElResult.error("密码不能为空");
            }
            if (sysUser.getSuTime() == null || sysUser.getSuTime().trim().isEmpty()) {
                sysUser.setSuTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }

            QueryWrapper<SysUser> qw = new QueryWrapper<>();
            qw.eq("su_name", sysUser.getSuName());
            if (sysUserService.count(qw) > 0) {
                return ElResult.error("用户名已存在");
            }

            // 对密码进行MD5加密
            sysUser.setSuPwd(DigestUtils.md5DigestAsHex(
                    sysUser.getSuPwd().getBytes(StandardCharsets.UTF_8)));

            boolean success = sysUserService.save(sysUser);
            return success ? ElResult.ok() : ElResult.error("新增用户失败");
        } catch (Exception e) {
            logger.error("新增失败: {}", e.getMessage(), e);
            return ElResult.error("新增失败：" + e.getMessage());
        }
    }

    // 修改用户 (简化时间处理, 修复密码更新逻辑)
    @PostMapping("/update")
    public ElResult<SysUser> updateUser(@RequestBody SysUser sysUser, HttpServletResponse response) {
        addSecurityHeaders(response);
        try {
            if (sysUser.getSuId() == null) {
                return ElResult.error("用户ID不能为空");
            }

            // 如果前端传了新的密码，就加密；如果没传，就设置为null，这样MyBatis-Plus就不会更新这个字段
            if (sysUser.getSuPwd() != null && !sysUser.getSuPwd().isEmpty()) {
                sysUser.setSuPwd(DigestUtils.md5DigestAsHex(
                        sysUser.getSuPwd().getBytes(StandardCharsets.UTF_8)));
            } else {
                sysUser.setSuPwd(null); // 设置为null，避免更新密码字段
            }

            boolean success = sysUserService.updateById(sysUser);
            return success ? ElResult.ok() : ElResult.error("修改用户失败");
        } catch (Exception e) {
            logger.error("修改失败: {}", e.getMessage(), e);
            return ElResult.error("修改失败：" + e.getMessage());
        }
    }

    // 删除用户
    @PostMapping("/delete")
    public ElResult<SysUser> deleteUser(@RequestBody SysUser sysUser, HttpServletResponse response) {
        addSecurityHeaders(response);
        if (sysUser.getSuId() == null) {
            return ElResult.error("用户ID不能为空");
        }
        boolean success = sysUserService.removeById(sysUser.getSuId());
        return success ? ElResult.ok() : ElResult.error("删除用户失败");
    }

    // 批量删除用户
    @PostMapping("/batchDelete")
    public ElResult<SysUser> batchDeleteUsers(@RequestBody List<Integer> userIds, HttpServletResponse response) {
        addSecurityHeaders(response);
        try {
            if (userIds == null || userIds.isEmpty()) {
                return ElResult.error("用户ID列表不能为空");
            }
            boolean success = sysUserService.removeByIds(userIds);
            return success ? ElResult.ok() : ElResult.error("批量删除用户失败");
        } catch (Exception e) {
            return ElResult.error("批量删除失败：" + e.getMessage());
        }
    }
}
