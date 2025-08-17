package cn.edu.qjnu.y2024.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 购物车项目表
 * </p>
 *
 * @author y2024
 * @since 2025-08-16
 */
@Data // 使用 @Data 注解自动生成 getter, setter, toString 等方法
@TableName("cart_items")
public class CartItems implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购物车项ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 客户ID, 关联customers表
     */
    private Integer customerId;

    /**
     * 商品ID, 关联products表
     */
    private String productId;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}