package com.book.movieticketbooking.useractivity.model;

public class Userfeedback {
    String id;
    String username;
    String ratingBar;
    String feedback;

    public Userfeedback() {
    }

    public Userfeedback(String id, String username, String ratingBar, String feedback) {
        this.id = id;
        this.username = username;
        this.ratingBar = ratingBar;
        this.feedback = feedback;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(String ratingBar) {
        this.ratingBar = ratingBar;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

}
