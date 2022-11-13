package com.onyshkiv.DAO.entity;

import java.util.Objects;

public class WayOfUsing extends Entity{
    private int wayOfUsingId;
    private String name;
    public WayOfUsing() {}
    public WayOfUsing(String name) {
        this.wayOfUsingId = 0;
        this.name = name;
    }

    public int getWayOfUsingId() {
        return wayOfUsingId;
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
        WayOfUsing that = (WayOfUsing) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "WayOfUsing{" +
                "wayOfUsingId=" + wayOfUsingId +
                ", name='" + name + '\'' +
                '}';
    }
}
