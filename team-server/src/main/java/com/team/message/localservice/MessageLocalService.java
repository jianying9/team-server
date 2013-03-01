package com.team.message.localservice;

import com.team.message.entity.MessageEntity;
import java.util.List;

/**
 *
 * @author zoe
 */
public interface MessageLocalService {

    public MessageEntity insertAndInquireUserMessage(String sendId, String receiveId, String message);
    
    public void readUserMessage(String messageId);
    
    public List<MessageEntity> inquireUnReadUserMessage(String receiveId);
}
