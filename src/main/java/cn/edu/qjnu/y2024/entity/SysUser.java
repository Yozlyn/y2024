package cn.edu.qjnu.y2024.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户实体类
 */
public class SysUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableId(value = "su_id", type = IdType.AUTO)
    private Integer suId;

    private String suName;

    private String suRole;

    // 存储MySQL兼容的时间格式(yyyy-MM-dd HH:mm:ss)
    private String suTime;

    private String suPwd;

    public Integer getSuId() {
        return suId;
    }

    @SuppressWarnings("unused")
    public void setSuId(Integer suId) {
        this.suId = suId;
    }

    public String getSuName() {
        return suName;
    }

    @SuppressWarnings("unused")
    public void setSuName(String suName) {
        this.suName = suName;
    }

    @SuppressWarnings("unused")
    public String getSuRole() {
        return suRole;
    }

    @SuppressWarnings("unused")
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

    @Override
    public String toString() {
        return "SysUser{" +
                "suId=" + suId +
                ", suName='" + suName + '\'' +
                ", suRole='" + suRole + '\'' +
                ", suTime='" + suTime + '\'' +
                ", suPwd='" + suPwd + '\'' +
                '}';
    }
}