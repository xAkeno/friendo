package com.example.friendo.FriendFeature.Repository;

import java.util.List;

public interface AccountFriendRepository {
    void deleteAccountFriend(int accountId, int friendId);

    List<Object[]> findFriend(int id);

    List<Object[]> findReverseFriend(int id);

    Object findFri(int id);
}
