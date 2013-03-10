package com.wolf.framework.dao;

import java.util.List;

/**
 *
 * @author aladdin
 */
public interface InquireKeyResult {

    public int getTotal();

    public int getPageSize();

    public List<String> getResultList();

    public String getNextPageIndex();

    public boolean isEmpty();
}
