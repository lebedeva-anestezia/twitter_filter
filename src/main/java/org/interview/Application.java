package org.interview;

import org.apache.log4j.Logger;
import org.interview.domain.TweetCollector;
import org.interview.domain.TweetSorter;
import org.interview.domain.model.Tweet;
import org.interview.domain.model.User;
import org.interview.serialize.TweetsSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Application implements ApplicationRunner {

    private final static Logger logger = Logger.getLogger(Application.class);

    @Autowired
    private TweetCollector tweetCollector;

    @Autowired
    private TweetSorter tweetSorter;

    @Autowired
    private TweetsSerializer tweetCollectionSerializer;

    private static final String MAX_NUMBER_OF_TWEETS_OPTION_NAME = "max_tweets";
    private static final String TIMEOUT_OPTION_NAME = "timeout";
    private static final String OUTPUT_FILE_OPTION_NAME = "output";

    private static final int DEFAULT_MAX_NUMBER_OF_TWEETS = 100;
    private static final long DEFAULT_TIMEOUT_MS = 30000L;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        int maxNumberOfTweets = DEFAULT_MAX_NUMBER_OF_TWEETS;
        long timeoutMs = DEFAULT_TIMEOUT_MS;
        String outputFileName = null;
        if (applicationArguments.containsOption(MAX_NUMBER_OF_TWEETS_OPTION_NAME)) {
            maxNumberOfTweets = getMaxNumberOfTweets(applicationArguments);
        }

        logger.info("Max number of tweets: " + maxNumberOfTweets);

        if (applicationArguments.containsOption(TIMEOUT_OPTION_NAME)) {
            timeoutMs = getTimeout(applicationArguments);
        }

        logger.info("Timeout: " + timeoutMs);

        if (applicationArguments.containsOption(OUTPUT_FILE_OPTION_NAME)) {
            outputFileName = getOutputFilename(applicationArguments);
            logger.info("Output file: " + outputFileName);
        }

        List<Tweet> tweets = tweetCollector.collectFilteredTweets("bieber", maxNumberOfTweets, timeoutMs);
        Map<User, List<Tweet>> groupedTweets = tweetSorter.groupByUser(tweets);
        groupedTweets = tweetSorter.sortByUserCreatedAt(groupedTweets);
        groupedTweets = tweetSorter.sortByTweetsCreatedAt(groupedTweets);

        if (outputFileName != null) {
            try (OutputStream outputStream = new PrintStream(outputFileName)) {
                tweetCollectionSerializer.printTweetsByUserCollection(outputStream, groupedTweets);
            }
        } else {
            tweetCollectionSerializer.printTweetsByUserCollection(System.out, groupedTweets);
        }
    }

    private int getMaxNumberOfTweets(ApplicationArguments applicationArguments) {
        int maxNumberOfTweets;
        List<String> maxNumberOfTweetsList = applicationArguments.getOptionValues(MAX_NUMBER_OF_TWEETS_OPTION_NAME);
        if (maxNumberOfTweetsList.size() != 1) {
            throw new IllegalArgumentException("max_tweets should be one value");
        }
        try {
            maxNumberOfTweets = Integer.valueOf(maxNumberOfTweetsList.get(0));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("max_tweets should be integer value");
        }
        if (maxNumberOfTweets <= 0) {
            throw new IllegalArgumentException("max_tweets should be positive integer value");
        }
        return maxNumberOfTweets;
    }

    private long getTimeout(ApplicationArguments applicationArguments) {
        long maxNumberOfTweets;
        List<String> timeoutMsList = applicationArguments.getOptionValues(TIMEOUT_OPTION_NAME);
        if (timeoutMsList.size() != 1) {
            throw new IllegalArgumentException("timeout should be one value");
        }
        try {
            maxNumberOfTweets = Long.valueOf(timeoutMsList.get(0));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("timeout should be integer value");
        }
        if (maxNumberOfTweets <= 0) {
            throw new IllegalArgumentException("timeout should be positive integer value");
        }
        return maxNumberOfTweets;
    }

    private String getOutputFilename(ApplicationArguments applicationArguments) {
        List<String> fileNameList = applicationArguments.getOptionValues(TIMEOUT_OPTION_NAME);
        if (fileNameList.size() != 1) {
            throw new IllegalArgumentException("output file name should be one value");
        }
        String fileName = fileNameList.get(0);
        if (!Files.exists(Paths.get(fileName))) {
            throw new IllegalArgumentException("output file does not exist");
        }
        return fileName;
    }
}
