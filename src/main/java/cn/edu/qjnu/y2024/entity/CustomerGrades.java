package cn.edu.qjnu.y2024.entity;

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
 * 客户等级表
 * </p>
 *
 * @author y2024
 * @since 2025-08-13
 */
@Getter
@Setter
@ToString
@TableName("customer_grades")
public class CustomerGrades implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 等级ID
     */
    @TableId("cg_id")
    private Integer cgId;

    /**
     * 等级名称
     */
    @TableField("cg_name")
    private String cgName;

    /**
     * 最低积分
     */
    @TableField("min_points")
    private Integer minPoints;

    /**
     * 最高积分
     */
    @TableField("max_points")
    private Integer maxPoints;

    /**
     * 折扣率
     */
    @TableField("cg_discount")
    private BigDecimal cgDiscount;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}


