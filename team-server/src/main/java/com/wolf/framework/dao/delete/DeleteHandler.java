package com.wolf.framework.dao.delete;

import java.util.List;

/**
 *
 * @author zoe
 */
public interface DeleteHandler {

    public void delete(String keyValue);

    public void batchDelete(List<String> keyValues);
}
