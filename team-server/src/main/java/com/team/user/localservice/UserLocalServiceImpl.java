package com.team.user.localservice;

import com.team.user.entity.FriendEntity;
import com.team.user.entity.UserEntity;
import com.wolf.framework.dao.REntityDao;
import com.wolf.framework.dao.annotation.InjectRDao;
import com.wolf.framework.dao.condition.InquireRedisIndexContext;
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

    @InjectRDao(clazz = UserEntity.class)
    private REntityDao<UserEntity> userEntityDao;
    //
    @InjectRDao(clazz = FriendEntity.class)
    private REntityDao<FriendEntity> friendEntityDao;

    @Override
    public void init() {
    }

    @Override
    public boolean isUserEmailExist(String userEmail) {
        boolean result = false;
        long num = this.userEntityDao.countByIndex("userEmail", userEmail);
        if (num > 0) {
            result = true;
        }
        return result;
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
        InquireRedisIndexContext inquireRedisIndexContext = new InquireRedisIndexContext("userEmail", userEmail);
        List<UserEntity> userEntityList = this.userEntityDao.inquireByIndex(inquireRedisIndexContext);
        if (userEntityList.isEmpty()) {
            userEntity = null;
        } else {
            userEntity = userEntityList.get(0);
        }
        return userEntity;
    }

    @Override
    public List<FriendEntity> inquireFriendByUserId(String userId, int pageIndex, int pageSize) {
        InquireRedisIndexContext inquireRedisIndexContext = new InquireRedisIndexContext("userId", userId);
        inquireRedisIndexContext.setPageSize(pageSize);
        inquireRedisIndexContext.setPageIndex(pageIndex);
        return this.friendEntityDao.inquireByIndex(inquireRedisIndexContext);
    }

    @Override
    public boolean isFriendIdExist(String userId, String friendId) {
        boolean result = false;
        List<FriendEntity> friendEntityList = this.inquireFriendByUserId(userId, 1, 100);
        for (FriendEntity friendEntity : friendEntityList) {
            if (friendEntity.getFriendId().equals(friendId)) {
                result = true;
                break;
            }
        }
        return result;
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
