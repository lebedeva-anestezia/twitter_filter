package org.interview.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import org.interview.domain.model.Tweet;
import org.interview.domain.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Service which provides {@link #printTweetsByUserCollection(OutputStream, Map)} method to print a map
 * of tweets grouped by user as JSON
 */
@Service
public class TweetsSerializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    static PrintableTweet convertTweet(Tweet tweet) {
        PrintableTweet printableTweet = new PrintableTweet();
        printableTweet.setId(tweet.getId());
        printableTweet.setCreatedAt(tweet.getCreatedAt());
        printableTweet.setText(tweet.getText());
        return printableTweet;
    }

    static PrintableUser convertUserWithTweets(User user, List<Tweet> tweets) {
        PrintableUser printableUser = new PrintableUser();
        printableUser.setId(user.getId());
        printableUser.setCreatedAt(user.getCreatedAt());
        printableUser.setName(user.getName());
        printableUser.setScreenName(user.getScreenName());
        printableUser.setTweets(tweets.stream().map(TweetsSerializer::convertTweet).collect(toList()));
        return printableUser;
    }

    /**
     * Prints the passed map of tweets grouped by user as JSON to the passed outputStream
     *
     * @param outputStream OutputStream to print JSON
     * @param map map of tweets grouped by user to be print
     * @throws IOException if an error occurred during printing to the passed outputStream
     */
    public void printTweetsByUserCollection(OutputStream outputStream, Map<User, List<Tweet>> map) throws IOException {

        List<PrintableUser> users = map.entrySet().stream()
                .map(entry -> TweetsSerializer.convertUserWithTweets(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        objectMapper.writeValue(outputStream, users);
    }
}
