package com.example.friendo.FriendFeature.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

import org.aspectj.lang.reflect.NoSuchAdviceException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.friendo.FriendoApplication;
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
    private static Logger logger = Logger.getLogger(FriendoApplication.class.getName());
    private FriendRepository friendRepository;
    private AccountRepository accountRepository;
    private AccountFriendRepository accountFriendRepository;
    @Autowired
    public FriendService(FriendRepository friendRepository,AccountRepository accountRepository,AccountFriendRepository accountFriendRepository){
        this.friendRepository = friendRepository;
        this.accountRepository = accountRepository;
        this.accountFriendRepository = accountFriendRepository;
    }

    @Transactional
    public Optional<?> request(String sendUsername, Integer targetAccountId) { // Renamed params for clarity
        // Initial check for null method parameters
        if (sendUsername == null || targetAccountId == null) {
            return Optional.empty();
        }

        Account sender = accountRepository.findByUsername(sendUsername).orElse(null);
        Account target = accountRepository.findById(targetAccountId).orElse(null);
        
        if (sender == null) {
            return Optional.empty();
        }
        if (target == null) {
            return Optional.empty();
        }

        // Now it's safe to print emails because sender and target are guaranteed non-null
        System.out.println(sender.getEmail() + " <- sender email");
        System.out.println(target.getEmail() + " <== target email");

        // Prevent friending self
        if (sender.getId().equals(target.getId())) { // Use .equals for Integer comparison
            System.out.println("same - cannot friend self");
            return Optional.of("same");
        }
        System.out.println("send id :" + sender.getId() + "target id : "+ target.getId());
        // Check for existing friendship
        Optional<Friend> existingFriendship = friendRepository.findByUserIdAndAccountId(sender.getId(), target.getId());
        System.out.println("heee");
        if (existingFriendship.isPresent()) {
            return Optional.of("already");
        }

        logger.info("No existing friendship found. Proceeding to create new PENDING request.");

        Friend newFriend = new Friend(); // Create a NEW Friend object
        newFriend.setCreatedAt(String.valueOf(LocalTime.now()));
        newFriend.setStatus(Status.PENDING); // Set initial status to PENDING
        newFriend.setUser_id(sender.getId()); // The user initiating the request

        newFriend.getAccount().add(target);

        System.out.println("New friend request created at: " + newFriend.getCreatedAt());
        System.out.println("successfully sent friend request!");

        return Optional.of(friendRepository.save(newFriend)); // Save the newly created Friend object
    }
    //accept friend
    @Transactional
    public Optional<?> accept(Integer sendid,Integer id){
        if(Optional.of(sendid).isPresent() && Optional.of(id).isPresent()){

            if (sendid.equals(id)) { // Use .equals for Integer comparison
                System.out.println("same - cannot friend self");
                return Optional.of("same");
            }

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
    public Optional<?> reject(Integer sendid,Integer id){
        if(Optional.of(sendid).isPresent() && Optional.of(id).isPresent()){
            if (sendid.equals(id)) { // Use .equals for Integer comparison
                System.out.println("same - cannot friend self");
                return Optional.of("same");
            }

            Optional<Friend> friendOpt = friendRepository.findByUserIdAndAccountId(id,sendid);

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
    public Optional<?> unfriend(Integer sendid,Integer id){
        if(Optional.of(sendid).isPresent() && Optional.of(id).isPresent()){
            
            if (sendid.equals(id)) { // Use .equals for Integer comparison
                System.out.println("same - cannot friend self");
                return Optional.of("same");
            }
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
                        newFriends.add(new AccountDTO(accF.get().getFirstname(),accF.get().getLastname(), accF.get().getId(),accF.get().getEmail(),accF.get().getUsername()));
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
                newFriends.add(new AccountDTO(accuser.get().getFirstname(),accuser.get().getLastname(),accuser.get().getId(),accuser.get().getEmail(),accuser.get().getUsername()));
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
