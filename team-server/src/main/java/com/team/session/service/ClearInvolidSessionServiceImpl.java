package com.team.session.service;

import com.team.session.localservice.SessionLocalService;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
//@ServiceConfig(
//        actionName = ActionNames.CLEAN_INVOLID_SESSION,
//        response = true,
//description = "清除无效的session")
public class ClearInvolidSessionServiceImpl implements Service {

    @InjectLocalService()
    private SessionLocalService sessionLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        this.sessionLocalService.celarInvolidSession();
        messageContext.success();
    }
}
