package cn.edu.qjnu.y2024.service.impl;

import cn.edu.qjnu.y2024.entity.ProductInventories;
import cn.edu.qjnu.y2024.mapper.ProductInventoriesMapper;
import cn.edu.qjnu.y2024.service.IProductInventoriesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品库存表 服务实现类
 * </p>
 *
 * @author y2024
 * @since 2025-08-13
 */
@Service
public class ProductInventoriesServiceImpl extends ServiceImpl<ProductInventoriesMapper, ProductInventories> implements IProductInventoriesService {

}
