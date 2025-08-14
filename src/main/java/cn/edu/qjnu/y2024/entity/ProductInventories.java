package cn.edu.qjnu.y2024.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品库存表
 * </p>
 *
 * @author y2024
 * @since 2025-08-13
 */
@Getter
@Setter
@ToString
@TableName("product_inventories")
public class ProductInventories implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 库存ID
     */
    @TableId("inventory_id")
    @JsonProperty("inventoryId")
    private String inventoryId;

    /**
     * 商品ID
     */
    @JsonProperty("productId")
    private String productId;

    /**
     * 存放位置
     */
    @TableField("location")
    @JsonProperty("location")
    private String location;

    /**
     * 数量
     */
    @JsonProperty("quantity")
    private Integer quantity;

    /**
     * 最后更新时间
     */
    @TableField("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @JsonProperty("lastUpdated")
    private LocalDateTime lastUpdated;

    /**
     * 创建时间
     */
    @TableField("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
}