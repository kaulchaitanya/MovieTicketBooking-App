package com.book.movieticketbooking.useractivity.model;

public class Booking {
    String BookingId;
    String MovieId;
    String MovieName;
    String UserName;
    String ShowTime;
    String BookingSeat;
    String Address;
    String Theatre;

    public Booking() {
    }

    public Booking(String bookingId,String movieId, String movieName, String userName, String showTime, String bookingSeat,String address,String theatre) {
        MovieId = movieId;
        BookingId = bookingId;
        MovieName = movieName;
        UserName = userName;
        ShowTime = showTime;
        BookingSeat = bookingSeat;
        Address = address;
        Theatre = theatre;
    }

    public String getBookingId() { return BookingId; }

    public void setBookingId(String bookingId) { BookingId = bookingId; }

    public String getMovieId() { return MovieId; }

    public void setMovieId(String movieId) { MovieId = movieId; }

    public String getMovieName() { return MovieName; }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getUserName() { return UserName; }

    public void setUserName(String userName) { UserName = userName; }

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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTheatre() { return Theatre; }

    public void setTheatre(String theatre) {
        Theatre = theatre;
    }
}
