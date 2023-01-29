package com.onyshkiv.entity;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class ActiveBook extends Entity{
    private int activeBookId;
    private Book book;
    private WayOfUsing wayOfUsing;
    private SubscriptionStatus subscriptionStatus;
    private Date startDate;
    private Date endDate;
    private Double fine;
    private Set<User> users;
    public ActiveBook() {}
    public ActiveBook(int activeBookId) {
        this.activeBookId = activeBookId;
    }
    public ActiveBook( Book book, WayOfUsing wayOfUsing, SubscriptionStatus subscriptionStatus, Date startDate, Date endDate, Double fine, Set<User> users) {
        this.activeBookId = 0;
        this.book = book;
        this.wayOfUsing = wayOfUsing;
        this.subscriptionStatus = subscriptionStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fine = fine;
        this.users = users;
    }
    public ActiveBook( int activeBookId, Book book, WayOfUsing wayOfUsing, SubscriptionStatus subscriptionStatus, Date startDate, Date endDate, Double fine, Set<User> users) {
        this.activeBookId = activeBookId;
        this.book = book;
        this.wayOfUsing = wayOfUsing;
        this.subscriptionStatus = subscriptionStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fine = fine;
        this.users = users;
    }

    public int getActiveBookId() {
        return activeBookId;
    }
    public void setActiveBookId(int activeBookId){this.activeBookId=activeBookId;}

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public WayOfUsing getWayOfUsing() {
        return wayOfUsing;
    }

    public void setWayOfUsing(WayOfUsing wayOfUsing) {
        this.wayOfUsing = wayOfUsing;
    }

    public SubscriptionStatus getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActiveBook that = (ActiveBook) o;
        return this.activeBookId==that.activeBookId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(activeBookId);
    }

    @Override
    public String toString() {
        return "ActiveBook{" +
                "activeBookId=" + activeBookId +
                ", book=" + book +
                ", wayOfUsing=" + wayOfUsing +
                ", subscriptionStatus=" + subscriptionStatus +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", fine=" + fine +
                ", users=" + users +
                '}';
    }
}
