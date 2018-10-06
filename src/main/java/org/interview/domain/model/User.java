package org.interview.domain.model;

import java.util.Date;

public class User extends Entity {

    private final String name;
    private final String screenName;

    private User(Long id, Date createdAt, String name, String screenName) {
        super(id, createdAt);
        this.name = name;
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Entity.Builder<User> {
        private String name;
        private String screenName;

        private Builder(){}

        public void setName(String name) {
            this.name = name;
        }

        public void setScreenName(String screenName) {
            this.screenName = screenName;
        }

        public User create() {
            return new User(id, createdAt, name, screenName);
        }
    }
}
