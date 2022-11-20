package com.onyshkiv.DAO.entity;

import java.util.Objects;

public class Role extends Entity{
    private int roleId;
    private String name;
    public Role(){}

    public Role(String name) {
        insertID(name);
        this.name = name;
    }

    public int getRole_id() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        insertID(name);
        this.name = name;
    }
    private void  insertID(String name){
        switch (name){
            case "reader": {
                roleId=1;
                break;
            }
            case "librarian":{
                roleId=2;
                break;
            }
            case "administrator":{
                roleId=3;
                break;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }
}
