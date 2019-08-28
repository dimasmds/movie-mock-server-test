package com.riotfallen.moviesearch.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.riotfallen.moviesearch.BuildConfig
import com.riotfallen.moviesearch.R
import com.riotfallen.moviesearch.entity.MovieItem
import com.squareup.picasso.Picasso


class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var movies: List<MovieItem> = listOf()

    fun setMovies(movies: List<MovieItem>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(movies[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var titleTextView: TextView = itemView.findViewById(R.id.rimTextViewTitle)
        private var descTextView: TextView = itemView.findViewById(R.id.rimTexTViewDesc)
        private var imageView: ImageView = itemView.findViewById(R.id.rimImageViewThumbnail)

        fun bindItem(movie: MovieItem) {
            titleTextView.text = movie.title
            descTextView.text = movie.overview
            Picasso.get().load(BuildConfig.IMAGE_BASE_URL + movie.backdropPath).fit().centerCrop().into(imageView)
        }
    }
}