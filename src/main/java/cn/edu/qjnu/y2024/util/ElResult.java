package cn.edu.qjnu.y2024.util;

import java.util.List;

@SuppressWarnings("unused")
public class ElResult<T> {
    private Integer code; // 状态码，0表示成功，其它数字表示失败
    private String msg;   // 提示信息
    private Long count;   // 记录总数（用于分页场景）
    private List<T> data; // 返回的数据列表

    public ElResult(Integer code, String msg, Long count, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public static <T> ElResult<T> ok(Long count, List<T> data) {
        return new ElResult<>(0, "", count, data);
    }

    public static <T> ElResult<T> ok() {
        return new ElResult<>(0, "", null, null);
    }

    public static <T> ElResult<T> ok(String msg) {
        return new ElResult<>(0, msg, null, null);
    }

    public static <T> ElResult<T> error(String msg) {
        return new ElResult<>(1, msg, null, null);
    }

    // --- Getters and Setters ---
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
