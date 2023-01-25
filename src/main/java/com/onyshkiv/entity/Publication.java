package com.onyshkiv.entity;

import java.util.Objects;

public class Publication extends Entity{
    private int publicationId;
    private String name;
    public Publication() {}

    public Publication(int publicationId, String name) {
        this.publicationId=publicationId;
        this.name = name;
    }

    public Publication(String name) {
        this.publicationId=0;
        this.name = name;
    }

    public int getPublicationId() {
        return publicationId;
    }
    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
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
        Publication that = (Publication) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Publication{" +
                "publicationId=" + publicationId +
                ", name='" + name + '\'' +
                '}';
    }
}
