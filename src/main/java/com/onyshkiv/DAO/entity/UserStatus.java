package com.onyshkiv.DAO.entity;

import java.util.Objects;

public class UserStatus extends Entity{
    private int userStatusId;
    private String name;

    public UserStatus(String name){
        this.userStatusId=0;
        this.name=name;
    }
    public int getUserStatusId() {
        return userStatusId;
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
        UserStatus that = (UserStatus) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
