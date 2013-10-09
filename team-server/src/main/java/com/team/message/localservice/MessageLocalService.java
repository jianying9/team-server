package com.team.message.localservice;

import com.team.message.entity.MessageEntity;
import com.wolf.framework.local.Local;
import java.util.List;

/**
 *
 * @author aladdin
 */
public interface MessageLocalService extends Local {

    public MessageEntity insertAndInquireUserMessage(String sendId, String receiveId, String message);

    public void readUserMessage(String messageId);

    public List<MessageEntity> inquireUnReadUserMessage(String receiveId);
}
