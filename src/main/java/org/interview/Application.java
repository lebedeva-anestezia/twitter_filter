package org.interview;

import org.interview.domain.TweetCollector;
import org.interview.domain.model.Tweet;
import org.interview.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private TweetCollector tweetCollector;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<User, List<Tweet>> groupedTweets = tweetCollector.collectFilteredTweets("bieber", 100, 30 * 1000L);
        for (Map.Entry<User, List<Tweet>> userListEntry : groupedTweets.entrySet()) {
            System.out.println(userListEntry.getKey().getName());
            System.out.println(userListEntry.getKey().getCreatedAt());
            System.out.println();
            for (Tweet tweet : userListEntry.getValue()) {
                System.out.println(tweet.getText());
                System.out.println(tweet.getCreatedAt());
            }
            System.out.println("----------------------------");
        }
    }
}
