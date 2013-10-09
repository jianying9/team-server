package com.team.message.localservice;

import com.team.message.entity.MessageEntity;
import com.wolf.framework.dao.EntityDao;
import com.wolf.framework.dao.annotation.InjectDao;
import com.wolf.framework.local.LocalServiceConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@LocalServiceConfig(
        interfaceInfo = MessageLocalService.class,
        description = "消息操作内部接口")
public class MessageLocalServiceImpl implements MessageLocalService {

    @InjectDao(clazz = MessageEntity.class)
    private EntityDao<MessageEntity> messageEntityDao;

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
        insertMap.put("isRead", "0");
        return this.messageEntityDao.insertAndInquire(insertMap);
    }

    @Override
    public void readUserMessage(String messageId) {
        Map<String, String> updateMap = new HashMap<String, String>(2, 1);
        updateMap.put("messageId", messageId);
        updateMap.put("isRead", "1");
        this.messageEntityDao.update(updateMap);
    }

    @Override
    public List<MessageEntity> inquireUnReadUserMessage(String userId) {
        List<MessageEntity> messgeResult = this.messageEntityDao.inquireByColumns("receiveId", userId, "isRead", "0");
        return messgeResult;
    }
}
