package com.framgia.music_28.screen.main.genre;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.music_28.R;
import com.framgia.music_28.data.model.Genre;

import java.util.ArrayList;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreHolder> {
    private ArrayList<Genre> mGenres;
    private Context mContext;

    public GenreAdapter(Context context, ArrayList<Genre> genres) {
        mContext = context;
        mGenres = genres;
    }

    @NonNull
    @Override
    public GenreHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_genre, viewGroup, false);
        return new GenreHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreHolder genreHolder, int i) {
        genreHolder.bindData(mGenres.get(i));
    }

    @Override
    public int getItemCount() {
        return mGenres.size() != 0 ? mGenres.size() : 0;
    }

    public static class GenreHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context mContext;
        private ImageButton mImageGenre;
        private TextView mTextGenreTitle;

        public GenreHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;
            mImageGenre = itemView.findViewById(R.id.image_genre);
            mTextGenreTitle = itemView.findViewById(R.id.text_genre_title);
            itemView.setOnClickListener(this);
        }

        private void bindData(Genre genre) {
            Glide.with(mContext).load(genre.getImageResource()).into(mImageGenre);
            mTextGenreTitle.setText(genre.getName());
        }

        @Override
        public void onClick(View v) {
        }
    }
}
