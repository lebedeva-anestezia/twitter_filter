package org.interview.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.domain.model.Tweet;
import org.interview.domain.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service which provides {@link #printTweetsByUserCollection(OutputStream, Map)} method to print a map
 * of tweets grouped by user as JSON
 */
@Service
public class TweetCollectionSerializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Prints the passed map of tweets grouped by user as JSON to the passed outputStream
     *
     * @param outputStream OutputStream to print JSON
     * @param map map of tweets grouped by user to be print
     * @throws IOException if an error occurred during printing to the passed outputStream
     */
    public void printTweetsByUserCollection(OutputStream outputStream, Map<User, List<Tweet>> map) throws IOException {

        List<PrintableUser> users = new ArrayList<>();
        for (Map.Entry<User, List<Tweet>> userTweetsEntry : map.entrySet()) {
            users.add(ModelToPrintableConverter.convertUserWithTweets(userTweetsEntry.getKey(), userTweetsEntry.getValue()));
        }
        objectMapper.writeValue(outputStream, users);
    }
}
