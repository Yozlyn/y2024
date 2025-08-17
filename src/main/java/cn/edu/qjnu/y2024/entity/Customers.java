package cn.edu.qjnu.y2024.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 客户信息表
 * </p>
 *
 * @author y2024
 * @since 2025-08-13
 */
@Getter
@Setter
@ToString
@TableName("customers")
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID（自增主键）
     */
    @TableId(value = "customer_id", type = IdType.AUTO)
    private Integer customerId;

    /**
     * 客户姓名
     */
    @TableField("customer_name")
    private String customerName;

    /**
     * 客户电话
     */
    @TableField("customer_phone")
    private String customerPhone;

    /**
     * 客户邮箱
     */
    @TableField("customer_email")
    private String customerEmail;

    /**
     * 客户密码
     */
    @TableField("password")
    private String password;

    /**
     * 性别（男、女、未知）
     */
    @TableField("customer_gender")
    private String customerGender;

    /**
     * 生日
     */
    @TableField("customer_birthday")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date customerBirthday;

    /**
     * 客户地址
     */
    @TableField("customer_address")
    private String customerAddress;

    /**
     * 客户等级（VIP、黄金、白银、普通）
     */
    @TableField("customer_level")
    private String customerLevel;

    /**
     * 注册时间
     */
    @TableField("registration_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime registrationDate;

    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastLoginTime;

    /**
     * 总订单数
     */
    @TableField("total_orders")
    private Integer totalOrders;

    /**
     * 总消费金额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 状态（正常、冻结、注销）
     */
    @TableField("status")
    private String status;

    /**
     * 创建时间
     */
    @TableField("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedAt;
}