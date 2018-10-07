package org.interview.serialize;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.domain.model.Tweet;
import org.interview.domain.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TweetCollectionSerializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void printTweetsByUserCollection(OutputStream outputStream, Map<User, List<Tweet>> map) throws IOException {

        List<PrintableUser> result = new ArrayList<>();
        for (Map.Entry<User, List<Tweet>> userTweetsEntry : map.entrySet()) {
            List<PrintableTweet> tweets = new ArrayList<>();
            for (Tweet tweet : userTweetsEntry.getValue()) {
                PrintableTweet printableTweet = new PrintableTweet();
                printableTweet.id = tweet.getId();
                printableTweet.createdAt = tweet.getCreatedAt();
                printableTweet.text = tweet.getText();
                tweets.add(printableTweet);
            }
            User user = userTweetsEntry.getKey();
            PrintableUser printableUser = new PrintableUser();
            printableUser.id = user.getId();
            printableUser.createdAt = user.getCreatedAt();
            printableUser.name = user.getName();
            printableUser.screenName = user.getScreenName();
            printableUser.tweets = tweets;
            result.add(printableUser);
        }
        objectMapper.writeValue(outputStream, result);
    }

    private static class PrintableUser {
        @JsonProperty("id")
        private Long id;
        @JsonProperty("created_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        private Date createdAt;
        @JsonProperty("name")
        private String name;
        @JsonProperty("screen_name")
        private String screenName;
        @JsonProperty("tweets")
        private List<PrintableTweet> tweets;
    }

    private static class PrintableTweet {
        @JsonProperty("id")
        private Long id;
        @JsonProperty("created_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        private Date createdAt;
        @JsonProperty("text")
        private String text;
    }
}
