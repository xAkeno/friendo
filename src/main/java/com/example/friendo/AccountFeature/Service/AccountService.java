package com.example.friendo.AccountFeature.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.management.RuntimeErrorException;

// import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.friendo.AccountExtraFeature.Model.AccountExtraModel;
import com.example.friendo.AccountExtraFeature.Repository.AccountExtraRepository;
import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.DTO.AccountProfileDTO;
import com.example.friendo.AccountFeature.DTO.LoginUserDto;
import com.example.friendo.AccountFeature.DTO.RegisterUserDto;
import com.example.friendo.AccountFeature.DTO.VerifyUserDto;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Model.Role;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.FeedFeature.DTO.CommentDTO;
import com.example.friendo.FeedFeature.DTO.FeedDTO;
import com.example.friendo.FeedFeature.DTO.LikeDTO;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.FeedFeature.Model.LikeFeed;
import com.example.friendo.FeedFeature.Repository.CommentRepository;
import com.example.friendo.FeedFeature.Repository.FeedRepository;
import com.example.friendo.FeedFeature.Repository.LikeRepository;
import com.example.friendo.MicrosoftAzure.ImageMetaDataRepository;
import com.example.friendo.MicrosoftAzure.ImageMetaModel;

import jakarta.mail.MessagingException;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private emailService EmailServices;
    private AccountExtraRepository accountExtraRepository;
    private FeedRepository feedRepository;
    private LikeRepository likeRepository;
    private CommentRepository commentRepository;
    private ImageMetaDataRepository imageMetaDataRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager,
        emailService EmailServices,
        AccountExtraRepository accountExtraRepository,
        FeedRepository feedRepository,
        LikeRepository likeRepository,
        CommentRepository commentRepository,
        ImageMetaDataRepository imageMetaDataRepository){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.EmailServices = EmailServices;
        this.accountExtraRepository = accountExtraRepository;
        this.feedRepository = feedRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.imageMetaDataRepository = imageMetaDataRepository;
    }

    //LogIn
    public Account Login(LoginUserDto input){
        Account account = accountRepository.findByUsername(input.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        if(!account.isEnabled()){
            throw new RuntimeException("Account not verified. Please verify your account");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(),input.getPassword()));
        return account;
    }
    public Account Register(RegisterUserDto input){
        Account account = new Account();
        account.setFirstname(input.getFirstname());
        account.setLastname(input.getLastname());
        account.setAge(input.getAge());
        account.setGender(input.getGender());
        account.setUsername(input.getUsername());
        account.setEmail(input.getEmail());
        account.setPassword(passwordEncoder.encode(input.getPassword()));
        account.setRole(Role.USER); // Always set a default role
        account.setVerification(generateVerificationCode());
        account.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        account.setEnabled(false);
        sendVerificationEmail(account);
        return accountRepository.save(account);
    }

    private void sendVerificationEmail(Account account) {
        String subject = "Account verification code";
        String veificationCode = account.getVerification();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + veificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        try{
            EmailServices.sendVerificationEmail(account.getEmail(),subject,htmlMessage);
        }catch(MessagingException e){
            e.printStackTrace();
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
    public void verifyUser(VerifyUserDto input){
        System.out.println("emeail : " + input.getEmail());
        Optional<Account> optionalUser = accountRepository.findByEmail(input.getEmail());
        if(optionalUser.isPresent()){
            System.out.println("boom");
            Account account = optionalUser.get();
            if(account.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())){
                throw new RuntimeException("Verification is expired");
            }
            if(account.getVerification().equals(input.getVerificationCode())){
                account.setEnabled(true);
                account.setVerification(null);
                account.setVerificationCodeExpiresAt(null);
                accountRepository.save(account);
            }else{
                throw new RuntimeException("Invalid verification code");
            }
        }else{
            System.out.println("heettt");
            // throw new RuntimeException("User not found");
        }
    }
    public void resendVerificationCode(String email){
        Optional<Account> opt = accountRepository.findByEmail(email);
        System.out.println(email + "<<<<");
        if(opt.isPresent()){
            Account account = opt.get();
            if(account.isEnabled()){
                throw new RuntimeException("Account is already verified");
            }
            account.setVerification(generateVerificationCode());
            account.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(account);
            accountRepository.save(account);
        }else{
            throw new RuntimeException("User not found");
        }
    }
    public AccountProfileDTO getUserOnProfile(Integer id, boolean isOwner){
        if(id == null){
            throw new RuntimeException("No id Found");
        }
        Account accountz = accountRepository.findById(id).get();
        Optional<AccountExtraModel> accountExtraMS = accountExtraRepository.findByAccount(id);
        AccountProfileDTO accountProfileDTO = new AccountProfileDTO();
        accountProfileDTO.set_Owner(isOwner);
        if(accountExtraMS.isPresent()){
            AccountExtraModel accountExtraM = accountExtraMS.get();
            accountProfileDTO.setBio(accountExtraM.getBio());
            accountProfileDTO.setCountry(accountExtraM.getCountry());
            accountProfileDTO.setCity(accountExtraM.getCity());
            accountProfileDTO.setSchool(accountExtraM.getSchool());
            accountProfileDTO.setProfileImg(accountExtraM .getProfileImg());
        }else{
            accountProfileDTO.setBio(null);
            accountProfileDTO.setCountry(null);
            accountProfileDTO.setCity(null);
            accountProfileDTO.setSchool(null);
            accountProfileDTO.setProfileImg(null);
        }
        
        List<Object[]> feeds = feedRepository.getFriendFeed(id);
        List<FeedDTO> newFeed = new ArrayList<>();
        Set<Integer> addedFeedIds = new HashSet<>();
        for (Object[] feed : feeds) {
            Integer feedId = (Integer) feed[0];
            //check if the user is already like
            Optional<LikeFeed> likes = likeRepository.findLiker(feedId, id);
            //get all those who like the feed
            List<Object[]> allWhoLike = likeRepository.getAllWhoLike(feedId);
            if (addedFeedIds.add(feedId)) { // Only add if not already present
                FeedDTO feedDTO = new FeedDTO();
                feedDTO.setId(feedId);
                feedDTO.setContext((String) feed[1]);
                feedDTO.setCreatedAt(String.valueOf(feed[2]));
                feedDTO.setVisibility((String) feed[3]);
                List<CommentDTO> comments = new ArrayList<>();
                List<Object[]> loadedComment = commentRepository.getAllComment(feedId);
                //loop to get all the comment
                for(Object[] commentRow : loadedComment){
                    CommentDTO comment = new CommentDTO();
                    comment.setId((Integer)commentRow[0]);
                    comment.setContent((String)commentRow[1]);
                    comment.setCreated_At((String)commentRow[2]);
                    Account creatorComment = accountRepository.findById((Integer) commentRow[3]).get();
                    if(Optional.of(creatorComment).isPresent()){
                        Account account = new Account();
                        account.setFirstname(creatorComment.getFirstname());
                        account.setLastname(creatorComment.getLastname());
                        account.setEmail(creatorComment.getEmail());
                        account.setId(creatorComment.getId());
                        account.setUsername(creatorComment.getUsername());
                        comment.setAccount(account);

                        accountExtraRepository.findByAccount(account.getId()).ifPresent(accountExtra -> {
                        String profileImg = accountExtra.getProfileImg();
                        System.out.println("Commnted profile is : " + profileImg);
                        if (profileImg != null) {
                            comment.setProfileImgUser(profileImg);
                        }
                    });
                    }
                    comments.add(comment);
                }
                feedDTO.setComments(comments);
                //get the creator
                Account account = accountRepository.findById((Integer) feed[4]).get();
                AccountDTO Passaccount = new AccountDTO();
                    Passaccount.setEmail(account.getEmail());
                    Passaccount.setFirstname(account.getFirstname());
                    Passaccount.setLastname(account.getLastname());
                    Passaccount.setId(account.getId());
                    Passaccount.setUsername(account.getUsername());

                accountExtraRepository.findByAccount(account.getId()).ifPresent(accountExtra -> {
                    String profileImg = accountExtra.getProfileImg();
                    if (profileImg != null) {
                        feedDTO.setProfileImg(profileImg);
                    }
                });


                // get the img of the post and put it on the list
                List<ImageMetaModel> imageList = new ArrayList<>();
                List<Object[]> loadedImage = imageMetaDataRepository.findByFeedId(feedId);
                if(loadedImage.isEmpty()){
                    System.out.println("No loaded image fround with id name " + feedId);
                }
                //load all image and put it on a llist
                for(Object[] imgRow : loadedImage){
                    ImageMetaModel image = new ImageMetaModel();
                    image.setId((Integer)imgRow[0]);
                    image.setImageUrl((String)imgRow[2]);
                    imageList.add(image);
                }
                feedDTO.setImageMetaModels(imageList);
                feedDTO.setAccount(Passaccount);

                //get All thee liker and check if they user alreaedy like it
                feedDTO.setLike(likes.isPresent() ? true : false);

                // //load all the likere into object
                List<LikeDTO> likeFeeds = new ArrayList<>();
                // List<AccountDTO> accountDTOs = new ArrayList<>();
                for(Object[] rowx : allWhoLike){
                    System.out.println(rowx[0] + "<<<" + rowx[1] + "<>>>" +rowx[2]);
                    Account userLiker = accountRepository.findById((Integer) rowx[1]).get();
                    AccountDTO userLikerDto = new AccountDTO();
                    userLikerDto.setEmail(userLiker.getEmail());
                    userLikerDto.setFirstname(userLiker.getFirstname());
                    userLikerDto.setLastname(userLiker.getLastname());
                    userLikerDto.setId(userLiker.getId());
                    LikeDTO likeDTO = new LikeDTO();
                    likeDTO.setAccount(userLikerDto);
                    likeFeeds.add(likeDTO);
                }
                feedDTO.setLikeFeed(likeFeeds);
                //     feedDTO.setLikeFeed(allWhoLike.get());
                newFeed.add(feedDTO);
            }
        }
        accountProfileDTO.setId(accountz.getId());
        accountProfileDTO.setFirstname(accountz.getFirstname());
        accountProfileDTO.setLastName(accountz.getLastname());
        accountProfileDTO.setUsername(accountz.getUsername());
        accountProfileDTO.setGender(accountz.getGender());
        
        accountProfileDTO.setFeed(newFeed);
        return accountProfileDTO;
    }
}

