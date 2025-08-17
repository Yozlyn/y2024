package cn.edu.qjnu.y2024.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付信息表
 * </p>
 *
 * @author y2024
 * @since 2025-08-13
 */
@Getter
@Setter
@ToString
@TableName("system_payments")
public class SystemPayments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付ID
     */
    @TableId("payment_id")
    private String paymentId;

    /**
     * 支付方式
     */
    @TableField("payment_method")
    private String paymentMethod;

    /**
     * 账户信息
     */
    @TableField("account_info")
    private String accountInfo;

    /**
     * 状态
     */
    private String status;

    /**
     * 最后更新时间
     */
    @TableField("last_updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private LocalDateTime lastUpdated;

    /**
     * 创建时间
     */
    @TableField("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private LocalDateTime createdAt;
}