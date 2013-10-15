package com.team.message.localservice;

import com.team.message.entity.MessageEntity;
import com.wolf.framework.local.Local;

/**
 *
 * @author aladdin
 */
public interface MessageLocalService extends Local {

    public MessageEntity insertAndInquireUserMessage(String sendId, String receiveId, String message);
}
