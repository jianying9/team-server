package com.wolf.framework.worker;

import com.wolf.framework.injecter.Injecter;
import com.wolf.framework.service.parameter.FieldContextBuilder;
import com.wolf.framework.service.parameter.ParametersContext;
import java.util.Map;

/**
 *
 * @author zoe
 */
public interface ServiceWorkerContext {

    public void putServiceWorker(final String actionName, final ServiceWorker serviceWorker, String className);

    public Map<String, ServiceWorker> getServiceWorkerMap();

    public boolean assertExistServiceWorker(final String actionName);

    public ParametersContext getParametersContextBuilder();

    public Injecter getInjecter();

    public FieldContextBuilder getFieldContextBuilder();

    public boolean isUsePseudo();
}
