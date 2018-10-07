package org.interview.serialize;

import org.interview.domain.model.Tweet;
import org.interview.domain.model.User;

import java.util.List;

import static java.util.stream.Collectors.toList;

class ModelToPrintableConverter {

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
        printableUser.setTweets(tweets.stream().map(ModelToPrintableConverter::convertTweet).collect(toList()));
        return printableUser;
    }
}
