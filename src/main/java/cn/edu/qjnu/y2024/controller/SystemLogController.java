package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.SystemLogs;
import cn.edu.qjnu.y2024.service.ISystemLogsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.edu.qjnu.y2024.util.ElResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/systemLog")
@CrossOrigin
public class SystemLogController {

    @Resource
    private ISystemLogsService systemLogService;

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
        Page<SystemLogs> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SystemLogs> queryWrapper = new QueryWrapper<>();
        Page<SystemLogs> systemLogPage = systemLogService.page(page, queryWrapper);
        return ElResult.ok(systemLogPage.getTotal(), systemLogPage.getRecords());
    }

    /**
     * 新增或更新
     * @param systemLog SystemLogs对象
     * @return ElResult对象
     */
    @PostMapping("/add")
    public ElResult save(@RequestBody SystemLogs systemLog) {
        systemLogService.saveOrUpdate(systemLog);
        return ElResult.ok();
    }

    /**
     * 删除
     * @param systemLog SystemLogs对象
     * @return ElResult对象
     */
    @PostMapping("/delete")
    public ElResult delete(@RequestBody SystemLogs systemLog) {
        systemLogService.removeById(systemLog.getLogId());
        return ElResult.ok();
    }

    /**
     * 更新
     * @param systemLog SystemLogs对象
     * @return ElResult对象
     */
    @PostMapping("/update")
    public ElResult update(@RequestBody SystemLogs systemLog) {
        systemLogService.updateById(systemLog);
        return ElResult.ok();
    }

    // 批量删除
    @PostMapping("/batchDelete")
    public ElResult batchDelete(@RequestBody List<String> logIds) {
        systemLogService.removeByIds(logIds);
        return ElResult.ok();
    }
}


