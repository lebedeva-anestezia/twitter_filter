package org.interview.domain.model;

public class Tweet extends Entity {
    private String text;
    private User user;

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }
}
