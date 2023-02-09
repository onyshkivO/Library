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
    private User user;
    private Integer quantity;
    public ActiveBook() {}
    public ActiveBook(int activeBookId) {
        this.activeBookId = activeBookId;
    }
    public ActiveBook( Book book, User user,WayOfUsing wayOfUsing, SubscriptionStatus subscriptionStatus, Date startDate, Date endDate,Integer quantity, Double fine) {
        this.activeBookId = 0;
        this.book = book;
        this.wayOfUsing = wayOfUsing;
        this.subscriptionStatus = subscriptionStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fine = fine;
        this.user = user;
        this.quantity=quantity;
    }
    public ActiveBook( int activeBookId, Book book,User user, WayOfUsing wayOfUsing, SubscriptionStatus subscriptionStatus, Date startDate, Date endDate,Integer quantity, Double fine) {
        this.activeBookId = activeBookId;
        this.book = book;
        this.wayOfUsing = wayOfUsing;
        this.subscriptionStatus = subscriptionStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fine = fine;
        this.user = user;
        this.quantity=quantity;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setFine(Double fine) {
        this.fine = fine;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
                ", user=" + user +
                ", quantity=" + quantity +
                '}';
    }
}
