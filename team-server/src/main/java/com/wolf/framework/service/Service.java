package com.wolf.framework.service;

import com.wolf.framework.worker.MessageContext;

/**
 *
 * @author zoe
 */
public interface Service {

    public void execute(MessageContext messageContext);
}
