package cn.edu.qjnu.y2024.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单列表表
 * </p>
 *
 * @author y2024
 * @since 2025-08-15
 */
@Getter
@Setter
@ToString
@TableName("order_list")
public class OrderList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId("order_id")
    private String orderId;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 客户名称
     */
    @TableField("customer_name")
    private String customerName;

    /**
     * 商品名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 总金额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 订单状态
     */
    @TableField("status")
    private String status;

    /**
     * 订单日期
     */
    @TableField("order_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private LocalDateTime orderDate;

    /**
     * 支付方式
     */
    @TableField("payment_method")
    private String paymentMethod;

    /**
     * 支付ID
     */
    @TableField("payment_id")
    private String paymentId;

    /**
     * 创建时间
     */
    @TableField("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private LocalDateTime updatedAt;
}