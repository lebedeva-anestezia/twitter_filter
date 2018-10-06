package org.interview.domain.model;

import java.util.Date;

public class Tweet extends Entity {
    private String text;
    private User user;

    public Tweet(Long id, Date createdAt, String text, User user) {
        super(id, createdAt);
        this.text = text;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Entity.Builder<Tweet> {
        private String text;
        private User user;

        private Builder(){}

        public void setText(String text) {
            this.text = text;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Tweet create() {
            return new Tweet(id, createdAt, text, user);
        }
    }
}
