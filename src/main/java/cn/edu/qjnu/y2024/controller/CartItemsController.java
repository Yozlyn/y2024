package cn.edu.qjnu.y2024.controller;

import cn.edu.qjnu.y2024.entity.CartItems;
import cn.edu.qjnu.y2024.service.ICartItemsService;
import cn.edu.qjnu.y2024.util.ElResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin
public class CartItemsController {

    @Resource
    private ICartItemsService cartItemService;

    private Integer getCurrentCustomerId() {
        return 1;
    }

    @GetMapping
    public ElResult<CartItems> getCart() {
        Integer customerId = getCurrentCustomerId();
        QueryWrapper<CartItems> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id", customerId);
        List<CartItems> cartItems = cartItemService.list(queryWrapper);
        return ElResult.ok((long) cartItems.size(), cartItems);
    }

    @PostMapping("/items")
    public ElResult addItem(@RequestBody CartItems request) {
        Integer customerId = getCurrentCustomerId();
        QueryWrapper<CartItems> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id", customerId);
        queryWrapper.eq("product_id", request.getProductId());
        CartItems existingItem = cartItemService.getOne(queryWrapper);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            cartItemService.updateById(existingItem);
        } else {
            request.setCustomerId(customerId);
            cartItemService.save(request);
        }
        return ElResult.ok();
    }

    @PutMapping("/items/{id}")
    public ElResult updateItemQuantity(@PathVariable Long id, @RequestBody CartItems request) {
        Integer customerId = getCurrentCustomerId();
        CartItems itemToUpdate = cartItemService.getById(id);

        if (itemToUpdate == null || !itemToUpdate.getCustomerId().equals(customerId)) {
            return ElResult.error("操作失败：购物车项不存在或权限不足");
        }

        itemToUpdate.setQuantity(request.getQuantity());
        cartItemService.updateById(itemToUpdate);
        return ElResult.ok();
    }

    @DeleteMapping("/items/{id}")
    public ElResult removeItem(@PathVariable Long id) {
        Integer customerId = getCurrentCustomerId();
        CartItems itemToRemove = cartItemService.getById(id);

        if (itemToRemove == null || !itemToRemove.getCustomerId().equals(customerId)) {
            return ElResult.error("操作失败：购物车项不存在或权限不足");
        }

        cartItemService.removeById(id);
        return ElResult.ok();
    }
}
