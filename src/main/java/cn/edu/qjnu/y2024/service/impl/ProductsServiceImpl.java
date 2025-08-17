package cn.edu.qjnu.y2024.service.impl;

import cn.edu.qjnu.y2024.entity.Products;
import cn.edu.qjnu.y2024.mapper.ProductsMapper;
import cn.edu.qjnu.y2024.service.IProductsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author y2024
 * @since 2025-08-13
 */
@Service
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements IProductsService {

}
