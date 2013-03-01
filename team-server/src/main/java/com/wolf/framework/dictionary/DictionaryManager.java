package com.wolf.framework.dictionary;

import java.util.Set;

/**
 *
 * @author zoe
 */
public interface DictionaryManager {

    public long getDictionaryId(String dicName);

    public String getElementName(long dicId, String eleValue);

    public String getElementNames(long dicId, String eleValues);
    
    public boolean containsKey(long dicId, String eleValue);
    
    public Set<String> getKeySet(long dicId);
}
