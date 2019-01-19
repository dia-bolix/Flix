package com.example.calixta.flix.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.calixta.flix.R;
import com.example.calixta.flix.models.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

   Context context;
   List<Movie> movies;

    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    //responsible for populating the view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("smile", "OnCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        return  new ViewHolder(view);
    }
    //attach data at a specific position to a view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d("smile", "OnBindViewHolder:" + i);
        Movie movie = movies.get(i);
        viewHolder.bind(movie);


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    //define the view holder
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView tvPoster;

        //finding the view objects by their id
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            tvPoster = itemView.findViewById(R.id.tvPoster);
        }
        //here we are actually binding and setting the text shown on screen
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            //set the image shown depending on if phone is in landscape mode or portrait mode
            String imageurl = movie.getPosterpath();
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageurl = movie.getBackdrop_url();
            }
            Glide.with(context).load(imageurl).into(tvPoster);

        }
    }
}
