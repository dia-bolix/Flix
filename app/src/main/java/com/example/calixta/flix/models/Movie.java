package com.example.calixta.flix.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Parcel



public class Movie {
    //attributes all movies have
    String posterpath;
    String title;
    String overview;
    String backdrop_url;
    float rating;
    int id;

    //an empty constructor for the parcel library
    public Movie() {
    }

    //a constructor that takes a json object and creates a movie object
    public Movie(JSONObject jsonObject) throws JSONException {
        //from the json given back from the api, access specific data using keys
        //here we are extracting specific info from the json
        posterpath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backdrop_url = jsonObject.getString("backdrop_path");
        id = jsonObject.getInt("id");

        //big decimal used to get a float out
        rating = BigDecimal.valueOf(jsonObject.getDouble("vote_average")).floatValue();

    }

    //takes json array then iterates through array and generates a list of movies
    public static List<Movie> fromJsonArray(JSONArray movieArray) throws  JSONException{
        //create a new list
        List<Movie> movies = new ArrayList<>();
        //for the length of movies, add the movie object to the list
        //gets the json object at a specific point, the idx number...
        for (int i=0; i < movieArray.length(); i++) {
            movies.add(new Movie(movieArray.getJSONObject(i)));
        }
        return movies;
    }

    //to get data out of the movie class, methods to use in other files

    public String getPosterpath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", posterpath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdrop_url() {
        return String.format("https://image.tmdb.org/t/p/w342%s", backdrop_url);
    }

    public float getRating() {
        return rating;
    }
    public  int getId() {
        return id;
    }
}
