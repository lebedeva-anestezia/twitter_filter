package org.interview.domain;

import org.interview.domain.model.Tweet;
import org.interview.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Service which provides methods to filter and group Tweets
 */
@Service
public class TweetSorter {

    public Map<User, List<Tweet>> groupByUser(List<Tweet> tweets) {
        Map<User, List<Tweet>> map = new HashMap<>();
        for (Tweet tweet : tweets) {
            map.putIfAbsent(tweet.getUser(), new ArrayList<>());
            map.get(tweet.getUser()).add(tweet);
        }
        return map;
    }

    public Map<User, List<Tweet>> sortByUserCreatedAt(Map<User, List<Tweet>> map) {
        TreeMap<User, List<Tweet>> sortedMap = new TreeMap<>(Comparator.comparing(User::getCreatedAt));
        for (Map.Entry<User, List<Tweet>> userTweetsEntry : map.entrySet()) {
            sortedMap.put(userTweetsEntry.getKey(), userTweetsEntry.getValue());
        }

        return sortedMap;
    }

    public Map<User, List<Tweet>> sortByTweetsCreatedAt(Map<User, List<Tweet>> map) {
        Map<User, List<Tweet>> mapCopy = new LinkedHashMap<>(map);

        for (Map.Entry<User, List<Tweet>> userTweetsEntry : mapCopy.entrySet()) {
            userTweetsEntry.getValue().sort(Comparator.comparing(Tweet::getCreatedAt));
        }

        return mapCopy;
    }
}
