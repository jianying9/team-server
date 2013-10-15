package com.team.message.localservice;

import com.team.message.entity.MessageEntity;
import com.wolf.framework.dao.REntityDao;
import com.wolf.framework.dao.annotation.InjectRDao;
import com.wolf.framework.local.LocalServiceConfig;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@LocalServiceConfig(
        interfaceInfo = MessageLocalService.class,
        description = "消息操作内部接口")
public class MessageLocalServiceImpl implements MessageLocalService {

    @InjectRDao(clazz = MessageEntity.class)
    private REntityDao<MessageEntity> messageEntityDao;

    @Override
    public void init() {
    }

    @Override
    public MessageEntity insertAndInquireUserMessage(String sendId, String receiveId, String message) {
        Map<String, String> insertMap = new HashMap<String, String>(8, 1);
        insertMap.put("sendId", sendId);
        insertMap.put("receiveId", receiveId);
        insertMap.put("message", message);
        long createTime = System.currentTimeMillis();
        insertMap.put("createTime", Long.toString(createTime));
        return this.messageEntityDao.insertAndInquire(insertMap);
    }
}
