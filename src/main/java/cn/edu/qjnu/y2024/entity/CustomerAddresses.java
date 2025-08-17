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
 * 客户地址表
 * </p>
 *
 * @author y2024
 * @since 2025-08-13
 */
@Getter
@Setter
@ToString
@TableName("customer_addresses")
public class CustomerAddresses implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 地址ID
     */
    @TableId("address_id")
    private String addressId;

    /**
     * 客户ID
     */
    @TableField("customer_id")
    private String customerId;

    /**
     * 收货人姓名
     */
    @TableField("consignee_name")
    private String consigneeName;

    /**
     * 收货电话
     */
    @TableField("consignee_phone")
    private String consigneePhone;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区/县
     */
    private String district;

    /**
     * 详细地址
     */
    @TableField("detail_address")
    private String detailAddress;

    /**
     * 是否默认
     */
    @TableField("is_default")
    private Boolean isDefault;

    /**
     * 创建时间
     */
    @TableField("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
    private LocalDateTime updatedAt;
}


