package org.interview.serialize;

import org.interview.domain.model.Tweet;
import org.interview.domain.model.User;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static org.interview.TestUtils.createTweet;
import static org.interview.TestUtils.createUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TweetsSerializerTest {

    User user;

    Tweet tweet1;
    Tweet tweet2;

    @Before
    public void init() throws ParseException {
        user = createUser(1L, new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-05"), "user1", "userDisplay1");

        tweet1 = createTweet(1L, new SimpleDateFormat("yyyy-MM-dd").parse("2018-08-05"), "text1", user);
        tweet2 = createTweet(2L, new SimpleDateFormat("yyyy-MM-dd").parse("2018-03-05"), "text1", user);
    }

    @Test
    public void convertTweet() {
        PrintableTweet printableTweet = TweetsSerializer.convertTweet(tweet1);

        assertNotNull(printableTweet);
        assertEquals(tweet1.getId(), printableTweet.getId());
        assertEquals(tweet1.getCreatedAt(), printableTweet.getCreatedAt());
        assertEquals(tweet1.getText(), printableTweet.getText());
    }

    @Test
    public void testConvertUserWithTweets() {
        PrintableUser printableUser = TweetsSerializer.convertUserWithTweets(user, Arrays.asList(tweet1, tweet2));

        assertNotNull(printableUser);
        assertEquals(user.getId(), printableUser.getId());
        assertEquals(user.getCreatedAt(), printableUser.getCreatedAt());
        assertEquals(user.getName(), printableUser.getName());
        assertEquals(user.getScreenName(), printableUser.getScreenName());

        assertNotNull(printableUser.getTweets());
        assertEquals(2, printableUser.getTweets().size());
    }

}