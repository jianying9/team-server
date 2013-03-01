package com.wolf.framework.lucene;

import com.wolf.framework.task.Task;

/**
 *
 * @author zoe
 */
public class LuceneRotateTaskImpl implements Task{
    
    private final HdfsLuceneImpl hdfsLuceneImpl;

    public LuceneRotateTaskImpl(HdfsLuceneImpl hdfsLuceneImpl) {
        this.hdfsLuceneImpl = hdfsLuceneImpl;
    }
    
    @Override
    public void doWhenRejected() {
        this.hdfsLuceneImpl.releaseRotateLock();
    }

    @Override
    public void run() {
        this.hdfsLuceneImpl.rotate();
    }
}
