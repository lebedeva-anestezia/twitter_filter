package org.interview.domain;

import org.interview.domain.model.Tweet;
import org.interview.domain.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class TweetSorterTest {

    TweetSorter tweetSorter = new TweetSorter();

    User user1;
    User user2;

    Tweet tweet1;
    Tweet tweet2;
    Tweet tweet3;

    @Before
    public void init() throws ParseException {
        user1 = createUser(1L, new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-05"), "user1", "userDisplay1");
        user2 = createUser(2L, new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-05"), "user2", "userDisplay2");

        tweet1 = createTweet(1L, new SimpleDateFormat("yyyy-MM-dd").parse("2018-08-05"), "text1", user1);
        tweet2 = createTweet(2L, new SimpleDateFormat("yyyy-MM-dd").parse("2018-03-05"), "text1", user1);
        tweet3 = createTweet(3L, new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-05"), "text1", user2);
    }

    @Test
    public void testGroupByUser() {
        List<Tweet> tweets = Arrays.asList(tweet1, tweet2, tweet3);
        Map<User, List<Tweet>> groupedTweets = tweetSorter.groupByUser(tweets);

        assertNotNull(groupedTweets);
        assertEquals(2, groupedTweets.keySet().size());
        assertTrue(groupedTweets.containsKey(user1));
        assertEquals(2, groupedTweets.get(user1).size());

        assertTrue(groupedTweets.get(user1).contains(tweet1));
        assertTrue(groupedTweets.get(user1).contains(tweet2));

        assertTrue(groupedTweets.containsKey(user2));
        assertEquals(1, groupedTweets.get(user2).size());

        assertTrue(groupedTweets.get(user2).contains(tweet3));
    }

    @Test
    public void testSortByUserCreatedAt() {
        Map<User, List<Tweet>> groupedTweets = new HashMap<>();
        groupedTweets.put(user1, new ArrayList<>());
        groupedTweets.put(user2, new ArrayList<>());
        groupedTweets.get(user1).add(tweet1);
        groupedTweets.get(user1).add(tweet2);
        groupedTweets.get(user2).add(tweet3);

        Map<User, List<Tweet>> sortedMap = tweetSorter.sortByUserCreatedAt(groupedTweets);

        assertNotNull(sortedMap);
        assertEquals(2, sortedMap.keySet().size());

        ArrayList<User> users = new ArrayList<>(sortedMap.keySet());
        assertEquals(user2, users.get(0));
        assertEquals(user1, users.get(1));
    }

    @Test
    public void testSortByTweetCreatedAt() {
        Map<User, List<Tweet>> groupedTweets = new HashMap<>();
        groupedTweets.put(user1, new ArrayList<>());
        groupedTweets.put(user2, new ArrayList<>());
        groupedTweets.get(user1).add(tweet1);
        groupedTweets.get(user1).add(tweet2);
        groupedTweets.get(user2).add(tweet3);

        Map<User, List<Tweet>> sortedMap = tweetSorter.sortByTweetsCreatedAt(groupedTweets);

        assertNotNull(sortedMap);
        assertEquals(2, sortedMap.keySet().size());

        assertEquals(tweet2, sortedMap.get(user1).get(0));
        assertEquals(tweet1, sortedMap.get(user1).get(1));
    }

    private User createUser(Long id, Date createdAt, String name, String screenName) {
        User.Builder userBuilder = User.builder();
        userBuilder.setId(id);
        userBuilder.setCreatedAt(createdAt);
        userBuilder.setName(name);
        userBuilder.setScreenName(screenName);
        return userBuilder.create();
    }

    private Tweet createTweet(Long id, Date createAt, String text, User user) {
        Tweet.Builder builder = Tweet.builder();
        builder.setId(id);
        builder.setCreatedAt(createAt);
        builder.setText(text);
        builder.setUser(user);
        return builder.create();
    }

}