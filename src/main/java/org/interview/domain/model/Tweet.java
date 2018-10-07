package org.interview.domain.model;

import java.util.Date;

public class Tweet extends Entity {
    private String text;
    private User user;

    private Tweet(Long id, Date createdAt, String text, User user) {
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

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Tweet create() {
            return new Tweet(id, createdAt, text, user);
        }
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .add("text", text)
                .add("user", user)
                .add("id", id)
                .add("createdAt", createdAt)
                .toString();
    }
}
