package com.team.config;

/**
 *
 * @author aladdin
 */
public enum GlobalLockEnum {

    SESSION_LOCK {
        @Override
        public long getLockId() {
            return 1;
        }
        
        @Override
        public String getDescription(){
            return "保证用户session唯一";
        }
    },
    USER_EMAIL_LOCK {
        @Override
        public long getLockId() {
            return 2;
        }
        
        @Override
        public String getDescription(){
            return "保证注册邮箱唯一";
        }
    };

    public abstract long getLockId();
    
    public abstract String getDescription();
}
