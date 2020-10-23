package com.book.movieticketbooking.theatreactivity.model;

public class Downloadmovies {
    String Uid;
    String movieName;
    String movieLink;

    public Downloadmovies(){
    }

    public Downloadmovies(String uid, String movieName, String movieLink) {
        Uid = uid;
        this.movieName = movieName;
        this.movieLink = movieLink;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieLink() {
        return movieLink;
    }

    public void setMovieLink(String movieLink) {
        this.movieLink = movieLink;
    }
}
