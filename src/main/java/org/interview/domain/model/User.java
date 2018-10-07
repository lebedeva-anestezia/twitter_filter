package org.interview.domain.model;

import com.google.common.base.Objects;

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

    public static class Builder extends Entity.Builder<Builder> {
        private String name;
        private String screenName;

        private Builder(){}

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setScreenName(String screenName) {
            this.screenName = screenName;
            return this;
        }

        public User create() {
            return new User(id, createdAt, name, screenName);
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("screenName", screenName)
                .add("id", id)
                .add("createdAt", createdAt)
                .toString();
    }
}
