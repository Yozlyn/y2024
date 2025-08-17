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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品评价表
 * </p>
 *
 * @author y2024
 * @since 2025-08-13
 */
@Getter
@Setter
@ToString
@TableName("product_reviews")
public class ProductReviews implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评价ID
     */
    @TableId("review_id")
    @JsonProperty("reviewId")
    private String reviewId;

    /**
     * 商品ID
     */
    @TableField("product_id")
    @JsonProperty("productId")
    private String productId;

    /**
     * 客户ID
     */
    @TableField("customer_id")
    @JsonProperty("customerId")
    private String customerId;

    /**
     * 评分
     */
    @JsonProperty("rating")
    private Integer rating;

    /**
     * 评论内容
     */
    @JsonProperty("comment")
    private String comment;

    /**
     * 评价日期
     */
    @TableField("review_date")
    @JsonProperty("reviewDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private LocalDateTime reviewDate;

    /**
     * 创建时间
     */
    @TableField("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}