package com.team.session.service;

import com.team.config.ActionNames;
import com.team.session.localservice.SessionLocalService;
import com.wolf.framework.local.LocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.MessageContext;

/**
 *
 * @author zoe
 */
//@ServiceConfig(
//        actionName = ActionNames.CLEAN_INVOLID_SESSION,
//        response = true,
//description = "清除无效的session")
public class ClearInvolidSessionServiceImpl implements Service {

    @LocalService()
    private SessionLocalService sessionLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        this.sessionLocalService.celarInvolidSession();
        messageContext.success();
    }
}
