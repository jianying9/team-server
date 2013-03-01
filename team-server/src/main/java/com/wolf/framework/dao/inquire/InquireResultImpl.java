package com.wolf.framework.dao.inquire;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.InquireResult;
import java.util.List;

/**
 *
 * @author zoe
 */
public class InquireResultImpl<T extends Entity> implements InquireResult {

    private int total = 0;
    private int pageSize = 1;
    private String nextPageIndex = "";
    private List<T> resultList;

    @Override
    public int getTotal() {
        return this.total;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public List getResultList() {
        return this.resultList;
    }

    @Override
    public String getNextPageIndex() {
        return this.nextPageIndex;
    }

    @Override
    public boolean isEmpty() {
        return this.resultList.isEmpty();
    }

    void setTotal(int total) {
        this.total = total;
    }

    void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    void setNextPageIndex(String nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }

    void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
