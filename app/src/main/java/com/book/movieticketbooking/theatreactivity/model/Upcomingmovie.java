package com.book.movieticketbooking.theatreactivity.model;

public class Upcomingmovie {
    String MovieId;
    String Movie_Name;
    String Movie_Year;

    public Upcomingmovie(){
    }

    public Upcomingmovie(String movieId, String movie_Name, String movie_Year) {
        MovieId = movieId;
        Movie_Name = movie_Name;
        Movie_Year = movie_Year;
    }

    public String getMovieId() {
        return MovieId;
    }

    public void setMovieId(String movieId) {
        MovieId = movieId;
    }

    public String getMovie_Name() {
        return Movie_Name;
    }

    public void setMovie_Name(String movie_Name) {
        Movie_Name = movie_Name;
    }

    public String getMovie_Year() {
        return Movie_Year;
    }

    public void setMovie_Year(String movie_Year) {
        Movie_Year = movie_Year;
    }
}
