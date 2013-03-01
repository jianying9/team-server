package com.wolf.framework.websocket;

import com.sun.grizzly.websockets.DataFrame;
import com.sun.grizzly.websockets.WebSocketListener;
import com.wolf.framework.config.FrameworkLoggerEnum;
import com.wolf.framework.logger.LogFactory;
import com.wolf.framework.session.Session;
import org.slf4j.Logger;

/**
 *
 * @author zoe
 */
public final class GlobalTimerSocketImpl implements GlobalWebSocket {
    
    private final Logger logger = LogFactory.getLogger(FrameworkLoggerEnum.FRAMEWORK);
    
    private void notSupportedInfo() {
        logger.info("timer inoke: Not supported yet.");
    }
    
    @Override
    public Session getSession() {
        this.notSupportedInfo();
        return null;
    }
    
    @Override
    public void setSession(Session session) {
        this.notSupportedInfo();
    }
    
    @Override
    public void send(String data) {
        this.logger.info("send:{}", data);
    }
    
    @Override
    public void send(byte[] data) {
        this.notSupportedInfo();
    }
    
    @Override
    public void sendPing(byte[] data) {
        this.notSupportedInfo();
    }
    
    @Override
    public void sendPong(byte[] data) {
        this.notSupportedInfo();
    }
    
    @Override
    public void stream(boolean last, String fragment) {
        this.notSupportedInfo();
    }
    
    @Override
    public void stream(boolean last, byte[] fragment, int off, int len) {
        this.notSupportedInfo();
    }
    
    @Override
    public void close() {
        this.notSupportedInfo();
    }
    
    @Override
    public void close(int code) {
        this.notSupportedInfo();
    }
    
    @Override
    public void close(int code, String reason) {
        this.notSupportedInfo();
    }
    
    @Override
    public boolean isConnected() {
        this.notSupportedInfo();
        return false;
    }
    
    @Override
    public void setClosed() {
        this.notSupportedInfo();
    }
    
    @Override
    public void onConnect() {
        this.notSupportedInfo();
    }
    
    @Override
    public void onMessage(String text) {
        this.notSupportedInfo();
    }
    
    @Override
    public void onMessage(byte[] data) {
        this.notSupportedInfo();
    }
    
    @Override
    public void onFragment(boolean last, String payload) {
        this.notSupportedInfo();
    }
    
    @Override
    public void onFragment(boolean last, byte[] payload) {
        this.notSupportedInfo();
    }
    
    @Override
    public void onClose(DataFrame frame) {
        this.notSupportedInfo();
    }
    
    @Override
    public void onPing(DataFrame frame) {
        this.notSupportedInfo();
    }
    
    @Override
    public void onPong(DataFrame frame) {
        this.notSupportedInfo();
    }
    
    @Override
    public boolean add(WebSocketListener listener) {
        this.notSupportedInfo();
        return false;
    }
    
    @Override
    public boolean remove(WebSocketListener listener) {
        this.notSupportedInfo();
        return false;
    }
}
