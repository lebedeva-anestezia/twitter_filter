package org.interview;

import org.interview.domain.model.Tweet;
import org.interview.domain.model.User;

import java.util.Date;

public class TestUtils {

    public static User createUser(Long id, Date createdAt, String name, String screenName) {
        User.Builder userBuilder = User.builder();
        userBuilder.setId(id);
        userBuilder.setCreatedAt(createdAt);
        userBuilder.setName(name);
        userBuilder.setScreenName(screenName);
        return userBuilder.create();
    }

    public static Tweet createTweet(Long id, Date createAt, String text, User user) {
        Tweet.Builder builder = Tweet.builder();
        builder.setId(id);
        builder.setCreatedAt(createAt);
        builder.setText(text);
        builder.setUser(user);
        return builder.create();
    }
}
