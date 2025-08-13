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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

    // 添加安全头
    private void addSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "no sniff");
        response.setHeader("X-Frame-Options", "DENY");
    }

    // 格式化时间
    private String formatDate(String frontTime) throws ParseException {
        String formattedTime = frontTime.replace("Z", "").replace("T", " ");
        if (formattedTime.contains(".")) {
            formattedTime = formattedTime.substring(0, formattedTime.indexOf("."));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = sdf.parse(formattedTime);
        return sdf.format(date);
    }

    // 获取用户列表
    @RequestMapping("/listUser")
    public ElResult<SysUser> listUser(HttpServletResponse response) {
        addSecurityHeaders(response);
        List<SysUser> users = sysUserService.list();
        return ElResult.ok((long) users.size(), users);
    }

    // 用户登录
    @RequestMapping("/login")
    public ElResult<SysUser> login(SysUser sysUser, HttpServletResponse response) {
        addSecurityHeaders(response);
        sysUser.setSuPwd(DigestUtils.md5DigestAsHex(
                sysUser.getSuPwd().getBytes(StandardCharsets.UTF_8)));
        QueryWrapper<SysUser> qw = new QueryWrapper<>(sysUser);
        List<SysUser> users = sysUserService.list(qw);
        if (users.size() == 1) {
            return ElResult.ok(1L, users);
        } else {
            return ElResult.error("用户名或密码错误");
        }
    }

    // 分页查询用户
    @RequestMapping("/pageUser")
    public ElResult<SysUser> pageUser(Integer pageNum, Integer pageSize, HttpServletResponse response) {
        addSecurityHeaders(response);
        if (pageNum == null || pageNum <= 0) pageNum = 1;
        if (pageSize == null || pageSize <= 0) pageSize = 10;

        Page<SysUser> page = new Page<>(pageNum, pageSize);
        Page<SysUser> userPage = sysUserService.page(page);
        return ElResult.ok(userPage.getTotal(), userPage.getRecords());
    }

    // 新增用户
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
                return ElResult.error("创建时间不能为空");
            }

            sysUser.setSuTime(formatDate(sysUser.getSuTime()));
            QueryWrapper<SysUser> qw = new QueryWrapper<>();
            qw.eq("su_name", sysUser.getSuName());
            if (sysUserService.count(qw) > 0) {
                return ElResult.error("用户名已存在");
            }

            sysUser.setSuPwd(DigestUtils.md5DigestAsHex(
                    sysUser.getSuPwd().getBytes(StandardCharsets.UTF_8)));

            boolean success = sysUserService.save(sysUser);
            return success ? ElResult.ok() : ElResult.error("新增用户失败");
        } catch (ParseException e) {
            logger.error("时间格式错误: {}", e.getMessage(), e);
            return ElResult.error("时间格式错误，请重新选择时间");
        } catch (Exception e) {
            logger.error("新增失败: {}", e.getMessage(), e);
            return ElResult.error("新增失败：" + e.getMessage());
        }
    }

    // 修改用户
    @PostMapping("/update")
    public ElResult<SysUser> updateUser(@RequestBody SysUser sysUser, HttpServletResponse response) {
        addSecurityHeaders(response);
        try {
            if (sysUser.getSuId() == null) {
                return ElResult.error("用户ID不能为空");
            }

            if (sysUser.getSuTime() != null && !sysUser.getSuTime().trim().isEmpty()) {
                sysUser.setSuTime(formatDate(sysUser.getSuTime()));
            }

            if (sysUser.getSuPwd() != null && !sysUser.getSuPwd().isEmpty()) {
                sysUser.setSuPwd(DigestUtils.md5DigestAsHex(
                        sysUser.getSuPwd().getBytes(StandardCharsets.UTF_8)));
            } else {
                SysUser oldUser = sysUserService.getById(sysUser.getSuId());
                if (oldUser == null) {
                    return ElResult.error("用户不存在");
                }
                sysUser.setSuPwd(oldUser.getSuPwd());
            }

            boolean success = sysUserService.updateById(sysUser);
            return success ? ElResult.ok() : ElResult.error("修改用户失败");
        } catch (ParseException e) {
            logger.error("时间格式错误: {}", e.getMessage(), e);
            return ElResult.error("时间格式错误，请重新选择时间");
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
}