package com.example.calixta.flix;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.calixta.flix.adapters.MoviesAdapter;
import com.example.calixta.flix.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    //example link
    private static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    List<Movie> movies;

    //Add RecyclerView support library to the gradle build file - done
    //Define a model class to use as the data source - done
    //Add a RecyclerView to your activity to display the items - done
    //Create a custom row layout XML file to visualize the item
    //Create a RecyclerView.Adapter and ViewHolder to render the item
    //Bind the adapter to the data source to populate the RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //binding the adapter to data, so lets get variables for recycler view and an instance of the adapter
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        final MoviesAdapter adapter = new MoviesAdapter(this, movies);
        //layout manager
        rvMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMovies.setAdapter(adapter);

        AsyncHttpClient client = new AsyncHttpClient();
        //make network request on MOVIEURL, since data is given back is json format create a handler
        client.get(MOVIE_URL, new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //get the json array with the name results (which is a list of json movies)
                        try {
                            JSONArray movieArray = response.getJSONArray("results");
                            //so reference to movie does not change(so adapter does not forget)
                            movies.addAll(Movie.fromJsonArray(movieArray));
                            //notify adapter of change
                            adapter.notifyDataSetChanged();
                            //log my data to make sure it is working as expected
                            Log.d("trail,", movies.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                }


        );
        class RecyclerDecoration extends RecyclerView.ItemDecoration{
            private int space;

            public RecyclerDecoration(int space) {
                this.space = space;
            }

            @Override
            public void getItemOffsets(Rect outRect, View view,
                                       RecyclerView parent, RecyclerView.State state) {
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;

                // Add top margin only for the first item to avoid double space between items
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = space;
                } else {
                    outRect.top = 0;
                }
            }
        }
        //implements the recycler decoration class, which adds spacing between items in recyclerview
        RecyclerDecoration decoration = new RecyclerDecoration(50);
        rvMovies.addItemDecoration(decoration);

    }
}


