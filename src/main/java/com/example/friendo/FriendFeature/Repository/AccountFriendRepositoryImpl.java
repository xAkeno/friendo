package com.example.friendo.FriendFeature.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class AccountFriendRepositoryImpl implements com.example.friendo.FriendFeature.Repository.AccountFriendRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void deleteAccountFriend(int accountId, int friendId) {
        entityManager.createNativeQuery("DELETE FROM account_friend WHERE account_id = :accountId AND friend_id = :friendId")
                .setParameter("accountId", accountId)
                .setParameter("friendId", friendId)
                .executeUpdate();
    }

    @Override
    public List<Object[]> findFriend(int id) {
        return entityManager.createNativeQuery("select * from account_friend where account_id = :id;")
            .setParameter("id", id)
            .getResultList();
        
    }

    @Override
    public List<Object[]> findReverseFriend(int id) {
        return entityManager.createNativeQuery("select * from account_friend where friend_id = :id;")
            .setParameter("id", id)
            .getResultList();
    }


    @Override
    public Object findFri(int id) {
        return entityManager.createNativeQuery("select * from account_friend where friend_id = :id;")
            .setParameter("id", id)
            .getResultList();
    }
}