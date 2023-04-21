package com.onyshkiv.entity;

import java.util.Objects;

public class SubscriptionStatus extends Entity{
    private int subscriptionStatusID;
    private String name;
    public SubscriptionStatus(String name) {
        insertID(name);
        this.name = name;
    }

    public SubscriptionStatus(Integer id) {
        insertName(id);
        this.subscriptionStatusID = id;
    }

    public int getSubscriptionStatusID() {
        return subscriptionStatusID;
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
                "subscriptionStatusID=" + subscriptionStatusID +
                ", name='" + name + '\'' +
                '}';
    }
    private void  insertID(String name){
        switch (name){
            case "active": {
                subscriptionStatusID =1;
                break;
            }
            case "returned": {
                subscriptionStatusID =2;
                break;
            }
            case "fined": {
                subscriptionStatusID =3;
                break;
            }
            case "waiting": {
                subscriptionStatusID =4;
                break;
            }
        }
    }


    private void  insertName(Integer id){
        switch (id){
            case 1: {
                name="active";
                break;
            }
            case 2: {
                name="returned";
                break;
            }
            case 3: {
                name="fined";
                break;
            }
            case 4: {
                name="waiting";
                break;
            }
        }
    }
}
