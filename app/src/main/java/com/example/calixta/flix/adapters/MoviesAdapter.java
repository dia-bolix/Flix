package com.example.calixta.flix.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.calixta.flix.MovieDetail;
import com.example.calixta.flix.R;
import com.example.calixta.flix.models.Movie;

import org.parceler.Parcels;

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
        ProgressBar loading;
        RelativeLayout container;


        //finding the view objects by their id
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            //to get the marque animation
            tvTitle.setSelected(true);

            //stops scrolling in recyclerview when textview is clicked (allows you to scroll through text)
            tvOverview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            //makes the textview scrollable
            tvOverview.setMovementMethod(new ScrollingMovementMethod());

            //getting a reference to each container from recyclerview
            container = itemView.findViewById(R.id.container);

            tvPoster = itemView.findViewById(R.id.tvPoster);
            loading = itemView.findViewById(R.id.progressBar);
        }
        //here we are actually binding and setting the text shown on screen
        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            //set the image shown depending on if phone is in landscape mode or portrait mode
            String imageurl = movie.getPosterpath();
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageurl = movie.getBackdrop_url();
            }
            loading.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(imageurl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            loading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            loading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(tvPoster);

            //do something if a recyclerview container is clicked (go to the detail activity)
            container.setOnClickListener(new View.OnClickListener() {
              @Override
                public void onClick(View v) {
                   //go to detail screen
                  Intent go = new Intent(context, MovieDetail.class);
                  go.putExtra("title", movie.getTitle());
                  //now that movie is a parseable object, can send the whole object in
                  go.putExtra("movie", Parcels.wrap(movie));

                  context.startActivity(go);

               }
         });

        }
    }
}

