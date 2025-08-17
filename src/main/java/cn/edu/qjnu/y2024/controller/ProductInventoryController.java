package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.ProductInventories;
import cn.edu.qjnu.y2024.service.IProductInventoriesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.edu.qjnu.y2024.util.ElResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/productInventory")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST})
public class ProductInventoryController {

    @Resource
    private IProductInventoriesService productInventoryService;

    /**
     * 分页查询
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return ElResult对象，包含分页数据
     */
    @GetMapping("/page")
    public ElResult findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ProductInventories> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ProductInventories> queryWrapper = new QueryWrapper<>();
        Page<ProductInventories> productInventoryPage = productInventoryService.page(page, queryWrapper);
        return ElResult.ok(productInventoryPage.getTotal(), productInventoryPage.getRecords());
    }

    /**
     * 新增
     * @param productInventory ProductInventories对象
     * @return ElResult对象
     */
    @PostMapping("/add")
    public ElResult add(@RequestBody ProductInventories productInventory) {
        productInventoryService.save(productInventory);
        return ElResult.ok();
    }

    /**
     * 更新
     * @param productInventory ProductInventories对象
     * @return ElResult对象
     */
    @PostMapping("/update")
    public ElResult update(@RequestBody ProductInventories productInventory) {
        productInventoryService.updateById(productInventory);
        return ElResult.ok();
    }

    /**
     * 删除
     * @param productInventory ProductInventories对象
     * @return ElResult对象
     */
    @PostMapping("/delete")
    public ElResult delete(@RequestBody ProductInventories productInventory) {
        productInventoryService.removeById(productInventory.getInventoryId());
        return ElResult.ok();
    }

    /**
     * 批量删除
     * @param inventoryIds 库存ID列表
     * @return ElResult对象
     */
    @PostMapping("/batchDelete")
    public ElResult batchDelete(@RequestBody List<String> inventoryIds) {
        productInventoryService.removeByIds(inventoryIds);
        return ElResult.ok();
    }
}