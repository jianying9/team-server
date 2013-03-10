package com.wolf.framework.dao.inquire;

import com.wolf.framework.dao.InquireKeyResult;
import java.util.List;

/**
 *
 * @author aladdin
 */
public class InquireKeyResultImpl implements InquireKeyResult {

    private int total = 0;
    private int pageSize = 1;
    private String nextPageIndex = "";
    private List<String> resultList;

    @Override
    public int getTotal() {
        return this.total;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public List<String> getResultList() {
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

    void setResultList(List<String> resultList) {
        this.resultList = resultList;
    }
}
