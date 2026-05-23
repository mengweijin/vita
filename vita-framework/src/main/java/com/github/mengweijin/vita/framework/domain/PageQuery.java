package com.github.mengweijin.vita.framework.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页查询对象。在 {@link Page} 对象的属性的基础上增加 page 前缀，尽最大可能的避免与业务字段歧义。
 *
 * @author mengweijin
 * @since 2026/1/17
 */
@Data
@NoArgsConstructor
public class PageQuery<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    private long pageCurrent = 1;

    /**
     * 每页显示条数，默认 10
     */
    private long pageSize = 10;

    /**
     * 总数
     */
    private long pageTotal = 0;

    /**
     * 查询数据列表
     */
    @SuppressWarnings({"java:S1948"})
    private List<T> pageRecords;

    private PageQuery(final long pageCurrent, final long pageSize, final long pageTotal, final List<T> pageRecords) {
        this.pageCurrent = pageCurrent;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
        this.pageRecords = pageRecords;
    }

    public static <E> PageQuery<E> of(IPage<E> page) {
        return of(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    public static <E> PageQuery<E> of(long pageCurrent, long pageSize) {
        return of(pageCurrent, pageSize, 0);
    }

    public static <E> PageQuery<E> of(long pageCurrent, long pageSize, long pageTotal) {
        return of(pageCurrent, pageSize, pageTotal, null);
    }

    public static <E> PageQuery<E> of(long pageCurrent, long pageSize, long pageTotal, List<E> pageRecords) {
        return new PageQuery<>(pageCurrent, pageSize, pageTotal, pageRecords);
    }

    public IPage<T> toPage() {
        IPage<T> page = new Page<>(this.pageCurrent, this.pageSize, this.pageTotal);
        page.setRecords(this.pageRecords);
        return page;
    }
}
