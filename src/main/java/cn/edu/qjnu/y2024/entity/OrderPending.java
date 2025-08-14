package cn.edu.qjnu.y2024.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 待处理订单表
 * </p>
 *
 * @author y2024
 * @since 2025-08-14
 */
@Getter
@Setter
@TableName("order_pending")
public class OrderPending implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("order_id")
    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("orderNo")
    private String orderNo;

    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("productId")
    private String productId;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;

    @JsonProperty("orderDate")
    private LocalDate orderDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("createdAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "OrderPending{" +
                "orderId=" + orderId +
                ", orderNo=" + orderNo +
                ", customerId=" + customerId +
                ", customerName=" + customerName +
                ", productId=" + productId +
                ", productName=" + productName +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                "}";
    }
}