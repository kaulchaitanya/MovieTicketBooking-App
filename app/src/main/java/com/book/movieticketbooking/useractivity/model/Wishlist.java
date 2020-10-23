package com.book.movieticketbooking.useractivity.model;

public class Wishlist {

    String film_id;
    String film_name;
    String film_year;

    public Wishlist() {
    }

    public Wishlist(String film_id, String film_name, String film_year) {
        this.film_id = film_id;
        this.film_name = film_name;
        this.film_year = film_year;
    }

    public String getFilm_id() {
        return film_id;
    }

    public void setFilm_id(String film_id) {
        this.film_id = film_id;
    }

    public String getFilm_name() {
        return film_name;
    }

    public void setFilm_name(String film_name) {
        this.film_name = film_name;
    }

    public String getFilm_year() {
        return film_year;
    }

    public void setFilm_year(String film_year) {
        this.film_year = film_year;
    }
}
