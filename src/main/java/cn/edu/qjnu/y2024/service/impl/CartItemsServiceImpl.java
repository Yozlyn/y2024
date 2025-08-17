package cn.edu.qjnu.y2024.service.impl;

import cn.edu.qjnu.y2024.entity.CartItems;
import cn.edu.qjnu.y2024.mapper.CartItemsMapper;
import cn.edu.qjnu.y2024.service.ICartItemsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 购物车项目表 服务实现类
 * </p>
 *
 * @author y2024
 * @since 2025-08-16
 */
@Service
public class CartItemsServiceImpl extends ServiceImpl<CartItemsMapper, CartItems> implements ICartItemsService {

}
