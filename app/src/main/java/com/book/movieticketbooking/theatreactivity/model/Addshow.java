package com.book.movieticketbooking.theatreactivity.model;

public class Addshow {
    String movieId;
    String Movie_Name;
    String Movie_Lang;
    String Director_Name;
    String Movie_Cast;
    String m_category;
    String Show_Time;
    String Available_Seat;
    String m_address;
    String Theatre_Name;
    String Price;

    public Addshow(){}

    public Addshow(String movieId, String movie_Name, String movie_Lang, String director_Name, String movie_Cast, String m_category, String show_Time,
                   String available_Seat, String m_address, String theatre_Name, String price) {
        this.movieId = movieId;
        Movie_Name = movie_Name;
        Movie_Lang = movie_Lang;
        Director_Name = director_Name;
        Movie_Cast = movie_Cast;
        this.m_category = m_category;
        Show_Time = show_Time;
        Available_Seat = available_Seat;
        this.m_address = m_address;
        Theatre_Name = theatre_Name;
        Price = price;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovie_Name() {
        return Movie_Name;
    }

    public void setMovie_Name(String movie_Name) {
        Movie_Name = movie_Name;
    }

    public String getMovie_Lang() {
        return Movie_Lang;
    }

    public void setMovie_Lang(String movie_Lang) {
        Movie_Lang = movie_Lang;
    }

    public String getDirector_Name() {
        return Director_Name;
    }

    public void setDirector_Name(String director_Name) {
        Director_Name = director_Name;
    }

    public String getMovie_Cast() {
        return Movie_Cast;
    }

    public void setMovie_Cast(String movie_Cast) {
        Movie_Cast = movie_Cast;
    }

    public String getM_category() {
        return m_category;
    }

    public void setM_category(String m_category) {
        this.m_category = m_category;
    }

    public String getShow_Time() {
        return Show_Time;
    }

    public void setShow_Time(String show_Time) {
        Show_Time = show_Time;
    }

    public String getAvailable_Seat() { return Available_Seat; }

    public void setAvailable_Seat(String available_Seat) { Available_Seat = available_Seat; }

    public String getM_address() {
        return m_address;
    }

    public void setM_address(String m_address) {
        this.m_address = m_address;
    }

    public String getTheatre_Name() {
        return Theatre_Name;
    }

    public void setTheatre_Name(String theatre_Name) {
        Theatre_Name = theatre_Name;
    }

    public String getPrice() { return Price; }

    public void setPrice(String price) { Price = price; }
}
