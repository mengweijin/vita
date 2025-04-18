package com.github.mengweijin.vita.framework.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vita.framework.constant.Const;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 表格数据响应对象
 *
 * @author mengweijin
 */
@Data
@NoArgsConstructor
@SuppressWarnings({"unused"})
public class TableR<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 总数
     */
    private long total;

    /**
     * 数据
     */
    @SuppressWarnings("java:S1948")
    private List<T> rows;

    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String msg;

    public static <T> TableR<T> build(IPage<T> page) {
        TableR<T> data = new TableR<>();
        data.setCode(HttpStatus.OK.value());
        data.setMsg(Const.SUCCESS);
        data.setRows(page.getRecords());
        data.setTotal(page.getTotal());
        return data;
    }

    public static <T> TableR<T> build(List<T> list) {
        return build(list, list.size());
    }

    public static <T> TableR<T> build(List<T> list, long total) {
        TableR<T> data = new TableR<>();
        data.setCode(HttpStatus.OK.value());
        data.setMsg(Const.SUCCESS);
        data.setRows(list);
        data.setTotal(total);
        return data;
    }

}
