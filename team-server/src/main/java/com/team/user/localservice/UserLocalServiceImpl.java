package com.team.user.localservice;

import com.team.user.entity.FriendEntity;
import com.team.user.entity.UserEntity;
import com.wolf.framework.dao.EntityDao;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.annotation.DAO;
import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.condition.OperateTypeEnum;
import com.wolf.framework.local.LocalServiceConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@LocalServiceConfig(
        interfaceInfo = UserLocalService.class,
description = "用户操作内部接口")
public class UserLocalServiceImpl implements UserLocalService {

    @DAO(clazz = UserEntity.class)
    private EntityDao<UserEntity> userEntityDao;
    //
    @DAO(clazz = FriendEntity.class)
    private EntityDao<FriendEntity> friendEntityDao;

    @Override
    public boolean isUserEmailExist(String userEmail) {
        int num = this.userEntityDao.count("userEmail", userEmail);
        return num == 0 ? false : true;
    }

    @Override
    public UserEntity inquireUserByUserId(String userId) {
        return this.userEntityDao.inquireByKey(userId);
    }

    @Override
    public List<UserEntity> inquireUserByUserIdList(List<String> userIdList) {
        return this.userEntityDao.inquireByKeys(userIdList);
    }

    @Override
    public UserEntity insertUser(Map<String, String> parameterMap) {
        parameterMap.put("createTime", Long.toString(System.currentTimeMillis()));
        UserEntity userEntity = this.userEntityDao.insertAndInquire(parameterMap);
        return userEntity;
    }

    @Override
    public UserEntity inquireUserByUserEmail(String userEmail) {
        UserEntity userEntity;
        List<UserEntity> userEntityList = this.userEntityDao.inquireByColumn("userEmail", userEmail);
        if (userEntityList.isEmpty()) {
            userEntity = null;
        } else {
            userEntity = userEntityList.get(0);
        }
        return userEntity;
    }

    @Override
    public InquireResult<UserEntity> searchUserByNickName(String nickName, int pageIndex, int pageSize) {
        InquireContext inquireContext = new InquireContext();
        inquireContext.setPageSize(pageSize);
        inquireContext.setPageIndex(pageIndex);
        Condition condition = new Condition("nickName", OperateTypeEnum.LIKE, nickName);
        inquireContext.addCondition(condition);
        return this.userEntityDao.inquirePageByCondition(inquireContext);
    }

    @Override
    public InquireResult<FriendEntity> inquireFriendByUserId(String userId, int pageIndex, int pageSize) {
        InquireContext inquireContext = new InquireContext();
        inquireContext.setPageSize(pageSize);
        inquireContext.setPageIndex(pageIndex);
        Condition condition = new Condition("userId", OperateTypeEnum.EQUAL, userId);
        inquireContext.addCondition(condition);
        return this.friendEntityDao.inquirePageByCondition(inquireContext);
    }

    @Override
    public List<FriendEntity> inquireFriendByUserId(String userId) {
        InquireContext inquireContext = new InquireContext();
        inquireContext.setPageSize(100);
        Condition condition = new Condition("userId", OperateTypeEnum.EQUAL, userId);
        inquireContext.addCondition(condition);
        return this.friendEntityDao.inquireByCondition(inquireContext);
    }

    @Override
    public boolean isFriendIdExist(String userId, String friendId) {
        int num = this.friendEntityDao.count("userId", userId, "friendId", friendId);
        return num == 0 ? false : true;
    }

    @Override
    public void insertFriend(String userId, String friendId) {
        Map<String, String> insertMap = new HashMap<String, String>(4, 1);
        insertMap.put("userId", userId);
        insertMap.put("friendId", friendId);
        insertMap.put("createTime", Long.toString(System.currentTimeMillis()));
        this.friendEntityDao.insert(insertMap);
    }
}
