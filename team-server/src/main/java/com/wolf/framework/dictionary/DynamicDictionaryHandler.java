package com.wolf.framework.dictionary;

import com.wolf.framework.dao.Entity;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zoe
 */
public interface DynamicDictionaryHandler<T extends Entity> {

    public Map<String, String> getDictionaryValues(final List<Long> keyList);

    public Map<String, String> getDictionaryValues(final long key);
}
