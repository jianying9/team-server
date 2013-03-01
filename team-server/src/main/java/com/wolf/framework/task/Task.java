package com.wolf.framework.task;

/**
 *
 * @author zoe
 */
public interface Task extends Runnable {
    
    public void doWhenRejected();
}
