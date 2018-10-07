package org.interview.domain.model;

import java.util.Date;

public abstract class Entity {
    protected final Long id;
    protected final Date createdAt;

    public Entity(Long id, Date createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public static class Builder<T extends Builder<T>> {
        protected Long id;
        protected Date createdAt;

        public T setId(Long id) {
            this.id = id;
            return (T) this;
        }

        public T setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return (T) this;
        }
    }
}
