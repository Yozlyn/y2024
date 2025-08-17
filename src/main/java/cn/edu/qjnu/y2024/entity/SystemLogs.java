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
 * 系统日志表
 * </p>
 *
 * @author y2024
 * @since 2025-08-13
 */
@Getter
@Setter
@ToString
@TableName("system_logs")
public class SystemLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId("log_id")
    private String logId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 操作
     */
    private String operation;

    /**
     * 操作时间
     */
    @TableField("operation_time")
    private LocalDateTime operationTime;

    /**
     * IP地址
     */
    @TableField("ip_address")
    private String ipAddress;

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


