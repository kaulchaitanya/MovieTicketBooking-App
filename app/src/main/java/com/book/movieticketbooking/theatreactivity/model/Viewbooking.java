package com.book.movieticketbooking.theatreactivity.model;

public class Viewbooking {
    String BookingId;
    String MovieId;
    String MovieName;
    String UserName;
    String ShowTime;
    String BookingSeat;

    public Viewbooking(){
    }

    public Viewbooking(String bookingId, String movieId, String movieName, String userName, String showTime, String bookingSeat) {
        BookingId = bookingId;
        MovieId = movieId;
        MovieName = movieName;
        UserName = userName;
        ShowTime = showTime;
        BookingSeat = bookingSeat;

    }

    public String getBookingId() {
        return BookingId;
    }

    public void setBookingId(String bookingId) {
        BookingId = bookingId;
    }

    public String getMovieId() {
        return MovieId;
    }

    public void setMovieId(String movieId) {
        MovieId = movieId;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getShowTime() {
        return ShowTime;
    }

    public void setShowTime(String showTime) {
        ShowTime = showTime;
    }

    public String getBookingSeat() {
        return BookingSeat;
    }

    public void setBookingSeat(String bookingSeat) {
        BookingSeat = bookingSeat;
    }

}
