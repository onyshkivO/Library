package com.onyshkiv.entity;

import java.util.Objects;

public class WayOfUsing extends Entity{
    private int wayOfUsingId;
    private String name;
    public WayOfUsing() {}
    public WayOfUsing(String name) {
        insertID(name);
        this.name = name;
    }
    public WayOfUsing(Integer id){
        insertName(id);
        this.wayOfUsingId=id;
    }
    public int getWayOfUsingId() {
        return wayOfUsingId;
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

    private void  insertID(String name){
        switch (name){
            case "subscription": {
                wayOfUsingId=1;
                break;
            }
            case "reading room":{
                wayOfUsingId=2;
                break;
            }
        }
    }

    private void  insertName(Integer id){
        switch (id){
            case 1: {
                name="subscription";
                break;
            }
            case 2:{
                name= "reading room";
                break;
            }
        }
    }
}
