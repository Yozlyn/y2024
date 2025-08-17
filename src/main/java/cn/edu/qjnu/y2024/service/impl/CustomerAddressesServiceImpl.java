package cn.edu.qjnu.y2024.service.impl;

import cn.edu.qjnu.y2024.entity.CustomerAddresses;
import cn.edu.qjnu.y2024.mapper.CustomerAddressesMapper;
import cn.edu.qjnu.y2024.service.ICustomerAddressesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户地址表 服务实现类
 * </p>
 *
 * @author y2024
 * @since 2025-08-13
 */
@Service
public class CustomerAddressesServiceImpl extends ServiceImpl<CustomerAddressesMapper, CustomerAddresses> implements ICustomerAddressesService {

}
