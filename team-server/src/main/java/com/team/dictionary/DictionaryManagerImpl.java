package com.team.dictionary;

import com.team.dictionary.entity.DictionaryEntity;
import com.team.dictionary.entity.ElementEntity;
import com.wolf.framework.dao.EntityDao;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.annotation.DAO;
import com.wolf.framework.dictionary.DictionaryManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author zoe
 */
public class DictionaryManagerImpl implements DictionaryManager {

    private final Map<Long, Map<String, String>> dictionaryMap = new HashMap<Long, Map<String, String>>(16, 1);
    @DAO(clazz = DictionaryEntity.class)
    private EntityDao<DictionaryEntity> dictionaryEntityDao;
    //
    @DAO(clazz = ElementEntity.class)
    private EntityDao<ElementEntity> elementEntityDao;

    private Map<String, String> inquireElementMap(long dicId) {
        Map<String, String> elementMap = this.dictionaryMap.get(dicId);
        if (elementMap == null) {
            InquireResult<ElementEntity> elementResult = this.elementEntityDao.inquireByColumn("dicId", Long.toString(dicId));
            if (!elementResult.isEmpty()) {
                String eleValue;
                List<ElementEntity> elementEntityList = elementResult.getResultList();
                elementMap = new HashMap<String, String>(elementEntityList.size(), 1);
                for (ElementEntity elementEntity : elementEntityList) {
                    eleValue = Long.toString(elementEntity.getEleValue());
                    elementMap.put(eleValue, elementEntity.getEleName());
                }
                this.dictionaryMap.put(dicId, elementMap);
            }
        }
        return elementMap;
    }

    @Override
    public String getElementName(long dicId, String eleValue) {
        Map<String, String> elementMap = this.inquireElementMap(dicId);
        String elementName = elementMap.get(eleValue);
        if (elementName == null) {
            elementName = "";
        }
        return elementName;
    }

    @Override
    public String getElementNames(long dicId, String eleValues) {
        String eleNames;
        if (eleValues.isEmpty()) {
            eleNames = "";
        } else {
            String[] eleValueArray = eleValues.split(",");
            if (eleValueArray.length == 1) {
                eleNames = this.getElementName(dicId, eleValues);
            } else {
                String eleName;
                StringBuilder eleNamesBuilder = new StringBuilder(eleValueArray.length * 8);
                Map<String, String> elementMap = this.inquireElementMap(dicId);
                for (String eleValue : eleValueArray) {
                    eleName = elementMap.get(eleValue);
                    eleNamesBuilder.append(eleName).append(',');
                }
                eleNamesBuilder.setLength(eleNamesBuilder.length() - 1);
                eleNames = eleNamesBuilder.toString();
            }
        }
        return eleNames;
    }

    @Override
    public long getDictionaryId(String dicName) {
        long dicId = -1;
//        List<Long> dicIdList = this.dictionaryEntityDao.inquireKeysByColumn("dicName", dicName);
//        if (dicIdList.isEmpty()) {
//            dicId = -1;
//        } else {
//            dicId = dicIdList.get(0);
//        }
        return dicId;
    }

    @Override
    public boolean containsKey(long dicId, String eleValue) {
        Map<String, String> elementMap = this.inquireElementMap(dicId);
        return elementMap.containsKey(eleValue);
    }

    @Override
    public Set<String> getKeySet(long dicId) {
        Map<String, String> elementMap = this.inquireElementMap(dicId);
        return elementMap.keySet();
    }
}
