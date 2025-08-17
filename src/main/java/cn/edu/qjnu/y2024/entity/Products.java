package cn.edu.qjnu.y2024.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品实体类
 * </p>
 *
 * @author y2024
 * @since 2025-08-13
 */
@Getter
@Setter
@ToString
@TableName("products")
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "product_id", type = IdType.ASSIGN_UUID)
    private String productId;

    @TableField("product_name")
    private String productName;

    /**
     * 关联到 product_categories 表的主键
     */
    @TableField("category_id")
    private String categoryId;

    private BigDecimal price;

    /**
     * 数据库字段名为 stock_quantity
     */
    @TableField("stock_quantity")
    private Integer stockQuantity;

    @TableField("status")
    private String status;

    private String description;

    @TableField("image_url")
    private String imageUrl;
}
