package cn.edu.qjnu.y2024.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 广告表
 * </p>
 *
 * @author y2024
 * @since 2025-08-13
 */
@Getter
@Setter
@ToString
@TableName("marketing_adverts")
public class MarketingAdverts implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 广告ID
     */
    @TableId("advert_id")
    private String advertId;

    /**
     * 广告名称
     */
    @TableField("advert_name")
    private String advertName;

    /**
     * 广告类型
     */
    @TableField("advert_type")
    private String advertType;

    /**
     * 图片URL
     */
    @TableField("image_url")
    private String imageUrl;

    /**
     * 链接URL
     */
    @TableField("link_url")
    private String linkUrl;

    /**
     * 开始日期
     */
    @TableField("start_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    private LocalDate startDate;

    /**
     * 结束日期
     */
    @TableField("end_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    private LocalDate endDate;

    /**
     * 状态
     */
    private String status;

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


