package com.onyshkiv.DAO.entity;

import java.util.Objects;

public class SubscriptionStatus extends Entity{
    private int publicationStatusId;
    private String name;
    public SubscriptionStatus() {}
    public SubscriptionStatus(String name) {
        publicationStatusId=0;
        this.name = name;
    }

    public int getPublicationStatusId() {
        return publicationStatusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionStatus that = (SubscriptionStatus) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "SubscriptionStatus{" +
                "publicationStatusId=" + publicationStatusId +
                ", name='" + name + '\'' +
                '}';
    }
}
