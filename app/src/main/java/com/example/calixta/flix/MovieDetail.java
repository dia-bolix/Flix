package com.example.calixta.flix;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.calixta.flix.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class MovieDetail extends YouTubeBaseActivity {

    TextView detail_title;
    TextView detail_overview;
    RatingBar ratingBar;
    Movie movie;
    public static final String key = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String  trailer_api = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    YouTubePlayerView YoutubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        detail_title = findViewById(R.id.detail_title);
        detail_overview = findViewById(R.id.detail_overview);
        ratingBar = findViewById(R.id.ratingBar);
        YoutubePlayerView = findViewById(R.id.player);

        //now we have the same movie object from the previous activity in our detail activity
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        detail_title.setText(movie.getTitle());
        detail_overview.setText(movie.getOverview());
        ratingBar.setRating(movie.getRating());


        //making network requests using the api link above
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(trailer_api, movie.getId()), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        //parsing here
                        try {
                            JSONArray res = response.getJSONArray("results");
                            //if there are no trailers to show then show the poster
                            if (res.length() == 0) {
                                return;
                            }
                            //otherwise, get the trailer link in the json, and cue it in the initialize youtube method
                            JSONObject movieTrailer = res.getJSONObject(0);
                            String youtubekey = movieTrailer.getString("key");
                            initalizeYoutube(youtubekey);
                            
                            
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                }
        );








    }

    private void initalizeYoutube(final String youtubekey) {
        //here we are actually playing videos
        YoutubePlayerView.initialize(key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(youtubekey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}
