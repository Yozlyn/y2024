package cn.edu.qjnu.y2024.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("system_logistics")
public class SystemLogistics {
    @TableId(value = "logistics_id", type = IdType.ASSIGN_UUID)
    private String logisticsId;
    @TableField("company_name")
    private String companyName;
    @TableField("tracking_number")
    private String trackingNumber;
    private String status;
    @TableField("delivery_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    private LocalDate deliveryDate;
    @TableField("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
    private LocalDateTime createdAt;
    @TableField("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
    private LocalDateTime updatedAt;
}


