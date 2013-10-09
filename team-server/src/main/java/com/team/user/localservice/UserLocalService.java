package com.team.user.localservice;

import com.team.user.entity.FriendEntity;
import com.team.user.entity.UserEntity;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.local.Local;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public interface UserLocalService extends Local{

    public boolean isUserEmailExist(String userEmail);

    public UserEntity inquireUserByUserEmail(String userEmail);

    public InquireResult<UserEntity> searchUserByNickName(String nickName, int pageIndex, int pageSize);

    public UserEntity inquireUserByUserId(String userId);

    public List<UserEntity> inquireUserByUserIdList(List<String> userIdList);

    public UserEntity insertUser(Map<String, String> parameterMap);

    public InquireResult<FriendEntity> inquireFriendByUserId(String userId, int pageIndex, int pageSize);

    public List<FriendEntity> inquireFriendByUserId(String userId);

    public boolean isFriendIdExist(String userId, String friendId);

    public void insertFriend(String userId, String friendId);
}
