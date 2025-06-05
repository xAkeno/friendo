package com.example.friendo.FriendFeature.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.aspectj.lang.reflect.NoSuchAdviceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.FriendFeature.DTO.AccountFriendDTO;
import com.example.friendo.FriendFeature.Model.Friend;
import com.example.friendo.FriendFeature.Model.Status;
import com.example.friendo.FriendFeature.Repository.AccountFriendRepository;
import com.example.friendo.FriendFeature.Repository.FriendRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
public class FriendService {
    private FriendRepository friendRepository;
    private AccountRepository accountRepository;
    private AccountFriendRepository accountFriendRepository;
    @Autowired
    public FriendService(FriendRepository friendRepository,AccountRepository accountRepository,AccountFriendRepository accountFriendRepository){
        this.friendRepository = friendRepository;
        this.accountRepository = accountRepository;
        this.accountFriendRepository = accountFriendRepository;
    }

    //request friend
    @Transactional
    public Optional<Friend> request(String sendid,Integer id){
        if(Optional.of(sendid).isPresent() && Optional.of(id).isPresent()){

            Account sender = accountRepository.findByEmail(sendid).orElse(null);
            Account target = accountRepository.findById(id).orElse(null);

            if(sender == null || target == null){
                return Optional.empty();
            }

            Friend friend2 = new Friend();
            friend2.setCreatedAt(String.valueOf(LocalTime.now()));
            friend2.setStatus(Status.PENDING);
            friend2.setUser_id(sender.getId());
            friend2.setAccount(List.of(target));

            return Optional.of(friendRepository.save(friend2));

        }else return Optional.empty();
    }

    //accept friend
    @Transactional
    public Optional<Friend> accept(Integer sendid,Integer id){
        if(Optional.of(sendid).isPresent() && Optional.of(id).isPresent()){

            Optional<Friend> friendOpt = friendRepository.findByUserIdAndAccountId(id,sendid);

            if(!friendOpt.isPresent()){
                return Optional.empty();
            }
            friendOpt.get().setStatus(Status.ACCEPTED);
            
            return Optional.of(friendRepository.save(friendOpt.get()));
        }
        return Optional.empty();
    }
    //reject
    @Transactional
    public Optional<Friend> reject(Integer sendid,Integer id){
        if(Optional.of(sendid).isPresent() && Optional.of(id).isPresent()){

            Optional<Friend> friendOpt = friendRepository.findByUserIdAndAccountId(sendid, id);

            if(!friendOpt.isPresent()){
                return Optional.empty();
            }
            friendOpt.get().setStatus(Status.REJECTED);

            return Optional.of(friendRepository.save(friendOpt.get()));
        }
        return Optional.empty();
    }

    //unfriend
    @Transactional
    public Optional<Friend> unfriend(Integer sendid,Integer id){
        if(Optional.of(sendid).isPresent() && Optional.of(id).isPresent()){

            Optional<Friend> friendOpt = friendRepository.findByUserIdAndAccountId(sendid, id);

            Friend friend = friendOpt.get();

            if(!friendOpt.isPresent() && friend.getStatus() != Status.ACCEPTED){
                return Optional.empty();
            }
            friendRepository.delete(friendOpt.get());
            accountFriendRepository.deleteAccountFriend(id, friendOpt.get().getId());
            return Optional.of(friend);
        }
        return Optional.empty();
    }

    //get all friend
    @Transactional
    public List<AccountDTO> viewAllFriend(Integer id){
        if(!Optional.of(id).isPresent()){
            return List.of();
        }

        List<AccountDTO> newFriends = new ArrayList<>();

        List<Object[]> resultMtoM = accountFriendRepository.findFriend(id);
        
        if(resultMtoM.isEmpty()){
            List<Object[]> reverse = friendRepository.findReverseFriend(id);

            for(Object[] no : reverse){
                List<Object[]> resultMtoMReverse = accountFriendRepository.findReverseFriend((int)no[0]);
                for(Object[] w : resultMtoMReverse){
                    List<Object> list2 = Arrays.asList(w);
                    // System.out.println(list2.get(0) + " = "+ list2.get(1));
                    Optional<Account> accF = accountRepository.findById((Integer) list2.get(1));
                    Optional<Friend> Idd = friendRepository.findById((Integer) list2.get(0));
                    
                    if(Idd.isEmpty()){
                        System.out.println("it is e mepty");
                        break;
                    }
                    if(Idd.get().getStatus() != Status.ACCEPTED){
                        System.out.println("no");
                    }else {
                        System.out.println("it is accept <==1");
                        newFriends.add(new AccountDTO(accF.get().getFirstname(),accF.get().getLastname(), accF.get().getId(),accF.get().getEmail()));
                    }
                }
            }
        }
        
        for(Object[] row : resultMtoM){
            List<Object> list1 = Arrays.asList(row);
            // System.out.println(list1.get(0) + " == "+ list1.get(1));
            Optional<Friend> s = friendRepository.findById((Integer) list1.get(0));
            
            Optional<Account> accuser = accountRepository.findById(s.get().getUser_id());

            if(accuser.isEmpty()){
                System.out.println("it is emepty");
                break;
            }
            if(s.get().getStatus() != Status.ACCEPTED){
            }else{
                System.out.println("it is accept <==2");
                newFriends.add(new AccountDTO(accuser.get().getFirstname(),accuser.get().getLastname(),accuser.get().getId(),accuser.get().getEmail()));
            } 

        }
        return newFriends;
    }

    @Transactional
    public List<AccountDTO> viewRequest(Integer id){
        if(id == null){
            return List.of();
        }
        List<Object[]> friends = friendRepository.findRequested(id);
        List<AccountDTO> data = new ArrayList<>();

        for(Object[] row : friends){
            List<Object[]> x = accountFriendRepository.findReverseFriend((int) row[0]);
            System.out.println(row[0]);
            for(Object[] a :x){
                System.out.println(a[0] + " = " + a[1]);
                Optional<Account> acc = accountRepository.findById((Integer)a[1]);
                AccountDTO newAcc = new AccountDTO();
                newAcc.setEmail(acc.get().getEmail());
                newAcc.setFirstname(acc.get().getFirstname());
                newAcc.setLastname(acc.get().getLastname());
                newAcc.setId(acc.get().getId());

                data.add(newAcc);
            }
        }
        return data;
    }
}
