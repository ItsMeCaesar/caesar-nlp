package com.hicaesar.nlp.vo;

import java.util.List;

/**
 *
 * @author samuelwaskow
 * @param <T>
 */
public final class PagedVO<T> {
    
    private List<T> list;
    private long totalSize;
    
    /**
     * Default Constructor
     */
    public PagedVO() {
        super();
    }

    /**
     * Constructor
     * 
     * @param list
     * @param totalSize 
     */
    public PagedVO(final List<T> list, final long totalSize) {
        this.list = list;
        this.totalSize = totalSize;
    }

    @Override
    public String toString() {
        return "{list=" + list + ", totalSize=" + totalSize + '}';
    }
    
    /* Gets And Sets */
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
    
}
