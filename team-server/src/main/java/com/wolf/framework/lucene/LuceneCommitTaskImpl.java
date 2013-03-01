package com.wolf.framework.lucene;

import com.wolf.framework.task.Task;

/**
 *
 * @author zoe
 */
public class LuceneCommitTaskImpl implements Task {

    private final HdfsLuceneImpl hdfsLuceneImpl;

    public LuceneCommitTaskImpl(HdfsLuceneImpl hdfsLuceneImpl) {
        this.hdfsLuceneImpl = hdfsLuceneImpl;
    }

    @Override
    public void doWhenRejected() {
        this.hdfsLuceneImpl.releaseCommitLock();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        this.hdfsLuceneImpl.commit();
    }
}
