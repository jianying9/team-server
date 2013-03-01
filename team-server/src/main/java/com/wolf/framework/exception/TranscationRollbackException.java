package com.wolf.framework.exception;

import com.wolf.framework.config.ResponseFlagType;

/**
 *
 * @author zoe
 */
public class TranscationRollbackException extends RuntimeException {

    private static final long serialVersionUID = 7667095710346041017L;
    private final ResponseFlagType responseFlagType;
    private final String info;

    public TranscationRollbackException(final ResponseFlagType responseFlagType) {
        super(responseFlagType.getFlagName());
        this.responseFlagType = responseFlagType;
        this.info = "";
    }

    public TranscationRollbackException(final ResponseFlagType responseFlagType, String info) {
        super(responseFlagType.getFlagName());
        this.responseFlagType = responseFlagType;
        this.info = info;
    }

    public ResponseFlagType getFlagName() {
        return this.responseFlagType;
    }

    public String getInfo() {
        return info;
    }
}
