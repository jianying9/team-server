package com.wolf.framework.dao;

import java.util.List;

/**
 *
 * @author aladdin
 */
public interface InquireResult<T extends Entity> {

    public int getTotal();

    public int getPageSize();

    public List<T> getResultList();

    public String getNextPageIndex();

    public boolean isEmpty();
}
