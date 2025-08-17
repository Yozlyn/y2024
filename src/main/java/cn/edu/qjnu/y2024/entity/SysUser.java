package cn.edu.qjnu.y2024.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户实体类 (已修复)
 */
public class SysUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "su_id", type = IdType.AUTO)
    private Integer suId;

    @TableField("su_name")
    private String suName;

    @TableField("su_role")
    private String suRole;

    @TableField("su_time")
    private String suTime;

    @TableField("su_pwd")
    private String suPwd;

    // 新增字段：邮箱
    @TableField("email")
    private String email;

    // 新增字段：状态
    @TableField("status")
    private String status;

    // 新增字段：头像
    @TableField("avatar")
    private String avatar;


    // --- Getters and Setters ---

    public Integer getSuId() {
        return suId;
    }

    public void setSuId(Integer suId) {
        this.suId = suId;
    }

    public String getSuName() {
        return suName;
    }

    public void setSuName(String suName) {
        this.suName = suName;
    }

    public String getSuRole() {
        return suRole;
    }

    public void setSuRole(String suRole) {
        this.suRole = suRole;
    }

    public String getSuTime() {
        return suTime;
    }

    public void setSuTime(String suTime) {
        this.suTime = suTime;
    }

    public String getSuPwd() {
        return suPwd;
    }

    public void setSuPwd(String suPwd) {
        this.suPwd = suPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "suId=" + suId +
                ", suName='" + suName + '\'' +
                ", suRole='" + suRole + '\'' +
                ", suTime='" + suTime + '\'' +
                ", suPwd='" + suPwd + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
