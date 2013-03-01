package com.wolf.framework.dao.parser;

import java.util.UUID;

/**
 *
 * @author zoe
 */
public class KeyHandlerImpl implements KeyHandler {

    private final String name;

    public KeyHandlerImpl(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String nextValue() {
        return UUID.randomUUID().toString();
    }
}
