package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.CustomerGrades;
import cn.edu.qjnu.y2024.service.ICustomerGradesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.edu.qjnu.y2024.util.ElResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customerGrade")
@CrossOrigin
public class CustomerGradeController {

    @Resource
    private ICustomerGradesService customerGradeService;

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
                           @RequestParam(required = false) String cgName,
                           @RequestParam(required = false) Integer cgId,
                           HttpServletResponse response) {
        addSecurityHeaders(response);
        Page<CustomerGrades> page = new Page<>(pageNum, pageSize);
        QueryWrapper<CustomerGrades> queryWrapper = new QueryWrapper<>();
        
        // 添加查询条件
        if (cgName != null && !cgName.trim().isEmpty()) {
            queryWrapper.like("cg_name", cgName);
        }
        if (cgId != null) {
            queryWrapper.eq("cg_id", cgId);
        }
        
        queryWrapper.orderByDesc("cg_id");
        Page<CustomerGrades> customerGradePage = customerGradeService.page(page, queryWrapper);
        return ElResult.ok(customerGradePage.getTotal(), customerGradePage.getRecords());
    }

    /**
     * 新增
     * @param customerGrade CustomerGrades对象
     * @return ElResult对象
     */
    @PostMapping("/add")
    public ElResult add(@RequestBody CustomerGrades customerGrade) {
        try {
            customerGradeService.save(customerGrade);
            return ElResult.ok();
        } catch (Exception e) {
            return ElResult.error("新增失败：" + e.getMessage());
        }
    }

    /**
     * 更新
     * @param customerGrade CustomerGrades对象
     * @return ElResult对象
     */
    @PostMapping("/update")
    public ElResult update(@RequestBody CustomerGrades customerGrade) {
        try {
            customerGradeService.updateById(customerGrade);
            return ElResult.ok();
        } catch (Exception e) {
            return ElResult.error("更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除
     * @param requestBody 包含gradeId的请求体
     * @return ElResult对象
     */
    @PostMapping("/delete")
    public ElResult delete(@RequestBody Map<String, Object> requestBody) {
        try {
            Object gradeIdObj = requestBody.get("gradeId");
            Integer gradeId = null;
            
            if (gradeIdObj instanceof String) {
                gradeId = Integer.parseInt((String) gradeIdObj);
            } else if (gradeIdObj instanceof Integer) {
                gradeId = (Integer) gradeIdObj;
            }
            
            if (gradeId != null) {
                customerGradeService.removeById(gradeId);
                return ElResult.ok();
            } else {
                return ElResult.error("删除失败：无效的等级ID");
            }
        } catch (Exception e) {
            return ElResult.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param requestBody 包含gradeIds的请求体
     * @return ElResult对象
     */
    @PostMapping("/batchDelete")
    public ElResult batchDelete(@RequestBody Map<String, Object> requestBody) {
        try {
            @SuppressWarnings("unchecked")
            List<String> gradeIds = (List<String>) requestBody.get("gradeIds");
            
            if (gradeIds != null && !gradeIds.isEmpty()) {
                customerGradeService.removeByIds(gradeIds);
                return ElResult.ok();
            } else {
                return ElResult.error("批量删除失败：无效的等级ID列表");
            }
        } catch (Exception e) {
            return ElResult.error("批量删除失败：" + e.getMessage());
        }
    }
}

