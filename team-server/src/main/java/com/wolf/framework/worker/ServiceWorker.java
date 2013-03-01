package com.wolf.framework.worker;

/**
 * 服务工作对象接口
 *
 * @author zoe
 */
public interface ServiceWorker {

    public void doWork(FrameworkMessageContext frameworkMessageContext);
}
