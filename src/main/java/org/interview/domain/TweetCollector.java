package org.interview.domain;

import org.apache.log4j.Logger;
import org.interview.connect.TweetStream;
import org.interview.connect.TwitterConnector;
import org.interview.domain.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service which provides functionality to obtain tweets in real-time
 */
@Service
public class TweetCollector {

    private final static Logger logger = Logger.getLogger(TweetCollector.class);

    private final TwitterConnector twitterConnector;

    @Autowired
    public TweetCollector(TwitterConnector twitterConnector) {
        this.twitterConnector = twitterConnector;
    }

    /**
     * Method which collects real-time tweets filtered by the passed token
     *
     * @param token token value to filter tweets
     * @param maxNumberOfTweets maximum number of tweets which can be returned
     * @param timeoutMs time during which this method collects tweets
     * @return list of filtered tweets
     * @throws IOException if an error occurred during connection to Twitter
     * @throws InterruptedException if current thread was interrupted
     */
    public List<Tweet> collectFilteredTweets(String token, Integer maxNumberOfTweets, Long timeoutMs) throws IOException, InterruptedException {
        TweetStream tweetStream = twitterConnector.getTweetStreamFilteredByToken(token);
        return collectTweets(tweetStream, maxNumberOfTweets, timeoutMs);
    }

    private List<Tweet> collectTweets(TweetStream tweetStream, Integer maxNumberOfTweets, Long timeoutMs) {

        long expirationTime = System.currentTimeMillis() + timeoutMs;
        List<Tweet> tweets = new ArrayList<>();

        logger.info("Start collecting tweets");

        try {
            while (!isTimeExpired(expirationTime)) {
                while (!isTimeExpired(expirationTime) && !tweetStream.isNextTweet()) {
                    Thread.sleep(10);
                }
                if (tweetStream.isNextTweet()) {
                    Tweet nextTweet = tweetStream.getNextTweet();
                    tweets.add(nextTweet);
                    logger.debug("New tweet added: " + nextTweet);
                    if (tweets.size() == maxNumberOfTweets) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Cannot read tweets ", e);
        } catch (InterruptedException e) {
            logger.error("Tweet collecting thread was interrupted ", e);
        }

        logger.info("Finish collecting tweets");

        return tweets;
    }

    private boolean isTimeExpired(long expirationTime) {
        return System.currentTimeMillis() >= expirationTime;
    }
}
