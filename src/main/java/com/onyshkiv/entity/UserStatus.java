package com.onyshkiv.entity;

import java.util.Objects;

public class UserStatus extends Entity {
    private int userStatusId;
    private String name;

    public UserStatus(String name) {
        insertID(name);
        this.name = name;
    }

    public UserStatus(Integer id) {
        insertName(id);
        this.userStatusId = id;
    }

    private void insertID(String name) {
        switch (name) {
            case "active": {
                userStatusId = 1;
                break;
            }
            case "blocked": {
                userStatusId = 2;
                break;
            }
        }
    }

    private void insertName(Integer id) {
        switch (id) {
            case 1: {
                name = "active";
                break;
            }
            case 2: {
                name = "blocked";
                break;
            }
        }
    }

    public int getUserStatusId() {
        return userStatusId;
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
        UserStatus that = (UserStatus) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "userStatusId=" + userStatusId +
                ", name='" + name + '\'' +
                '}';
    }
}
