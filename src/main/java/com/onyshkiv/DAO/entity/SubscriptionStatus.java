package com.onyshkiv.DAO.entity;

import java.util.Objects;

public class SubscriptionStatus extends Entity{
    private int SubscriptionStatusID;
    private String name;
    public SubscriptionStatus(String name) {
        insertID(name);
        this.name = name;
    }

    public int getPublicationStatusId() {
        return SubscriptionStatusID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        insertID(name);
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
                "subscriptionStatusID=" + SubscriptionStatusID +
                ", name='" + name + '\'' +
                '}';
    }
    private void  insertID(String name){
        switch (name){
            case "active": {
                SubscriptionStatusID=1;
                break;
            }
            case "returned": {
                SubscriptionStatusID=2;
                break;
            }
            case "fined": {
                SubscriptionStatusID=3;
                break;
            }
        }
    }
}
