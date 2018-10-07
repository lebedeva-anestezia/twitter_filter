package org.interview.serialize;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

class PrintableUser {

    private Long id;

    @JsonProperty("created_at")
    private Date createdAt;

    private String name;

    @JsonProperty("screen_name")
    private String screenName;

    private List<PrintableTweet> tweets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public List<PrintableTweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<PrintableTweet> tweets) {
        this.tweets = tweets;
    }
}
