package com.example.friendo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Model.Role;
import com.example.friendo.FeedFeature.DTO.FeedDTO;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.FeedFeature.Model.LikeFeed;
import com.example.friendo.FeedFeature.Model.Visibility;
import com.example.friendo.FeedFeature.Repository.FeedRepository;
import com.example.friendo.FeedFeature.Repository.LikeRepository;
import com.example.friendo.FeedFeature.Service.FeedService;
import com.example.friendo.FriendFeature.Service.FriendService;

@ExtendWith(MockitoExtension.class)
public class FeedServiceTest {
    @Mock
    FeedRepository feedRepository;

    @Mock
    LikeRepository likeRepository;
    
    @Mock
    FriendService friendService;


    @InjectMocks
    FeedService feedService;

    @Test
    public void createFeed(){
        Account account = new Account();
        account.setId(1);
        account.setFirstname("John");
        account.setLastname("Doe");
        account.setAge(20);
        account.setGender("Male");
        account.setUsername("johndoe");
        account.setEmail("john@example.com");
        account.setPassword("password123");
        account.setRole(Role.USER); // Assuming you have Role enum with USER
        account.setEnabled(true);

        Feed feed = new Feed();
        feed.setContext("this is a test");
        feed.setAccount(account);
        feed.setCreated_at("today");
        feed.setVisibility(Visibility.PUBLIC);

        assertEquals(account.getUsername(), feed.getAccount().getUsername());
        assertEquals("this is a test", feed.getContext());
        assertEquals(feed.getAccount().getId(), account.getId());
        System.out.println("good");
    }

    @Test
    public void testGetPublicFeedWithMultipleData() {
        Integer userId = 1;

        // Simulate 3 feeds returned from the database
        List<Object[]> mockFeedData = List.of(
            new Object[]{101, "First post", "2024-01-01", "PUBLIC", 11},
            new Object[]{102, "Second post", "2024-01-02", "PUBLIC", 12},
            new Object[]{103, "Third post", "2024-01-03", "PUBLIC", 13}
        );

        when(feedRepository.getAllPublicFeed()).thenReturn(mockFeedData);

        // Only feed 101 and 103 are liked by userId=1
        when(likeRepository.findLiker(101, userId)).thenReturn(Optional.of(new LikeFeed()));
        when(likeRepository.findLiker(102, userId)).thenReturn(Optional.empty());
        when(likeRepository.findLiker(103, userId)).thenReturn(Optional.of(new LikeFeed()));

        List<FeedDTO> result = feedService.getPublicFeed(userId);

        // Assertions
        assertEquals(3, result.size());

        FeedDTO first = result.get(0);
        assertEquals(101, first.getId());
        assertEquals("First post", first.getContext());
        assertTrue(first.isLike());

        FeedDTO second = result.get(1);
        assertEquals(102, second.getId());
        assertEquals("Second post", second.getContext());
        assertFalse(second.isLike());

        FeedDTO third = result.get(2);
        assertEquals(103, third.getId());
        assertEquals("Third post", third.getContext());
        assertTrue(third.isLike());
    }


}
