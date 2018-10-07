package org.interview.domain;

import org.interview.connect.TweetStream;
import org.interview.connect.TwitterConnector;
import org.interview.domain.model.Tweet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;
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
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void collectFilteredTweets() throws Exception {
        when(twitterConnector.getTweetStreamFilteredByToken(any())).thenReturn(tweetStream);
        when(tweetStream.isNextTweet()).thenReturn(true);
        when(tweetStream.getNextTweet()).thenReturn(Tweet.builder().create());

        List<Tweet> tweets = tweetCollector.collectFilteredTweets("token", 1, 1000L);

        assertEquals(1, tweets.size());
    }

}