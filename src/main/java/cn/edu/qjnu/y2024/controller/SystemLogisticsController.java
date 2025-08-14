package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.SystemLogistics;
import cn.edu.qjnu.y2024.service.ISystemLogisticsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.edu.qjnu.y2024.util.ElResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/systemLogistics")
@CrossOrigin // 添加跨域支持
public class SystemLogisticsController {

    @Resource
    private ISystemLogisticsService systemLogisticsService;

    /**
     * 分页查询
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @param companyName 公司名称（可选）
     * @param status 状态（可选）
     * @return ElResult对象，包含分页数据
     */
    @GetMapping("/page")
    public ElResult findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String companyName,
                             @RequestParam(required = false) String status) {
        Page<SystemLogistics> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SystemLogistics> queryWrapper = new QueryWrapper<>();

        // 添加查询条件
        if (companyName != null && !companyName.trim().isEmpty()) {
            queryWrapper.like("company_name", companyName);
        }
        if (status != null && !status.trim().isEmpty()) {
            queryWrapper.eq("status", status);
        }

        queryWrapper.orderByDesc("logistics_id");
        Page<SystemLogistics> systemLogisticsPage = systemLogisticsService.page(page, queryWrapper);
        return ElResult.ok(systemLogisticsPage.getTotal(), systemLogisticsPage.getRecords());
    }

    /**
     * 新增或更新
     * @param systemLogistics SystemLogistics对象
     * @return ElResult对象
     */
    @PostMapping("/add")
    public ElResult save(@RequestBody SystemLogistics systemLogistics) {
        try {
            systemLogisticsService.saveOrUpdate(systemLogistics);
            return ElResult.ok();
        } catch (Exception e) {
            return ElResult.error("新增或更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除
     * @param requestBody 包含logisticsId的请求体
     * @return ElResult对象
     */
    @PostMapping("/delete")
    public ElResult delete(@RequestBody java.util.Map<String, String> requestBody) {
        try {
            String logisticsId = requestBody.get("logisticsId");
            if (logisticsId != null && !logisticsId.isEmpty()) {
                systemLogisticsService.removeById(logisticsId);
                return ElResult.ok();
            } else {
                return ElResult.error("删除失败：无效的物流ID");
            }
        } catch (Exception e) {
            return ElResult.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param requestBody 包含logisticsIds的请求体
     * @return ElResult对象
     */
    @PostMapping("/batchDelete")
    public ElResult batchDelete(@RequestBody java.util.Map<String, java.util.List<String>> requestBody) {
        try {
            java.util.List<String> logisticsIds = requestBody.get("logisticsIds");
            if (logisticsIds != null && !logisticsIds.isEmpty()) {
                systemLogisticsService.removeByIds(logisticsIds);
                return ElResult.ok();
            } else {
                return ElResult.error("批量删除失败：无效的物流ID列表");
            }
        } catch (Exception e) {
            return ElResult.error("批量删除失败：" + e.getMessage());
        }
    }

    /**
     * 更新
     * @param systemLogistics SystemLogistics对象
     * @return ElResult对象
     */
    @PostMapping("/update")
    public ElResult update(@RequestBody SystemLogistics systemLogistics) {
        try {
            systemLogisticsService.updateById(systemLogistics);
            return ElResult.ok();
        } catch (Exception e) {
            return ElResult.error("更新失败：" + e.getMessage());
        }
    }
}