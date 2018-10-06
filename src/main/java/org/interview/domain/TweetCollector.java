package org.interview.domain;

import org.interview.domain.model.Tweet;
import org.interview.domain.model.User;
import org.interview.dto.TweetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class TweetCollector {

    private final TwitterConnector twitterConnector;

    @Autowired
    public TweetCollector(TwitterConnector twitterConnector) {
        this.twitterConnector = twitterConnector;
    }

    public Map<User, List<Tweet>> collectFilteredTweets(String token, Integer maxNumberOfTweets, Long timeoutMs) throws IOException, InterruptedException {
        TweetStream tweetStream = twitterConnector.getTweetStreamFilteredByToken(token);
        List<Tweet> tweets = collectTweets(tweetStream, maxNumberOfTweets, timeoutMs);
        return groupTweets(tweets);
    }

    private Map<User, List<Tweet>> groupTweets(List<Tweet> tweets) {
        Map<User, List<Tweet>> map = new TreeMap<>(Comparator.comparing(User::getCreatedAt));
        for (Tweet tweet : tweets) {
            map.putIfAbsent(tweet.getUser(), new ArrayList<>());
            map.get(tweet.getUser()).add(tweet);
        }
        for (List<Tweet> tweetList : map.values()) {
            tweetList.sort(Comparator.comparing(Tweet::getCreatedAt));
        }
        return map;
    }

    private List<Tweet> collectTweets(TweetStream tweetStream, Integer maxNumberOfTweets, Long timeoutMs) throws IOException, InterruptedException {

        long expirationTime = System.currentTimeMillis() + timeoutMs;
        List<Tweet> tweets = new ArrayList<>();

        try {
            while (System.currentTimeMillis() < expirationTime) {
                while (System.currentTimeMillis() < expirationTime && !tweetStream.isNextTweet()) {
                    Thread.sleep(10);
                }
                if (tweetStream.isNextTweet()) {
                    tweets.add(tweetStream.getNextTweet());
                    if (tweets.size() == maxNumberOfTweets) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return tweets;
    }
}
