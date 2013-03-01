package com.wolf.framework.task;

/**
 *
 * @author zoe
 */
public interface TaskExecutor {

    public void shutdown();
    
    public void submet(Task task);
}
