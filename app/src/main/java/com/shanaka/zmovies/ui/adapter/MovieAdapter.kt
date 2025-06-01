package com.shanaka.zmovies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shanaka.zmovies.R
import com.shanaka.zmovies.data.model.Genre
import com.shanaka.zmovies.data.model.Movie
import com.shanaka.zmovies.databinding.ItemMovieBinding

class MovieAdapter(
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList = listOf<Movie>()
    private var genreList = listOf<Genre>()

    fun submitList(movies: List<Movie>) {
        movieList = movies
        notifyDataSetChanged()
    }

    // Setter for genre list
    fun setGenres(genres: List<Genre>) {
        genreList = genres
        notifyDataSetChanged() // Refresh to show genre names
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
            Glide.with(binding.moviePoster.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_no_image)
                .error(R.drawable.ic_launcher_background)
                .into(binding.moviePoster)

            binding.root.setOnClickListener {
                onItemClick.invoke(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size
}
