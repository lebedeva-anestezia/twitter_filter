package org.interview.domain;

import org.interview.connect.TweetStream;
import org.interview.connect.TwitterConnector;
import org.interview.domain.model.Tweet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class TweetCollectorTest {

    @Mock
    TwitterConnector twitterConnector;

    @Mock
    TweetStream tweetStream;

    @InjectMocks
    TweetCollector tweetCollector;

    @Before
    public void init() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(tweetStream.getNextTweet())
                .thenReturn(Tweet.builder().setId(1L).create())
                .thenReturn(Tweet.builder().setId(2L).create())
                .thenReturn(Tweet.builder().setId(3L).create());
    }

    @Test
    public void collectTwoFilteredTweets() throws Exception {
        when(twitterConnector.getTweetStreamFilteredByToken(any())).thenReturn(tweetStream);
        when(tweetStream.isNextTweet()).thenReturn(true);
        when(tweetStream.getNextTweet()).thenReturn(Tweet.builder().create());

        List<Tweet> tweets = tweetCollector.collectFilteredTweets("token", 2, 1000L);

        assertEquals(2, tweets.size());
    }

    @Test
    public void collectOneFilteredTweetsDueToTimeout() throws Exception {
        when(twitterConnector.getTweetStreamFilteredByToken(any())).thenReturn(tweetStream);
        when(tweetStream.isNextTweet()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(tweetStream.getNextTweet()).thenReturn(Tweet.builder().create());

        List<Tweet> tweets = tweetCollector.collectFilteredTweets("token", 2, 1000L);

        assertEquals(1, tweets.size());
    }
}