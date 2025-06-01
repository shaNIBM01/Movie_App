package com.shanaka.zmovies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shanaka.zmovies.R
import com.shanaka.zmovies.data.model.Genre
import com.shanaka.zmovies.data.model.Movie
import com.shanaka.zmovies.databinding.ItemSearchBinding

class SearchAdapter(
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var movieList = listOf<Movie>()
    private var genreList = listOf<Genre>()

    fun submitList(movies: List<Movie>) {
        movieList = movies
        notifyDataSetChanged()
    }

    fun setGenres(genres: List<Genre>) {
        genreList = genres
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieYear.text = movie.releaseDate?.take(4) ?: "Unknown"

            val genreNames = genreList.filter { movie.genreIds.contains(it.id) }
                .joinToString(", ") { it.name }
            binding.movieGenres.text = if (genreNames.isNotBlank()) genreNames else "No genres"

            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
            Glide.with(binding.moviePoster.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_no_image)
                .error(R.drawable.ic_no_image)
                .into(binding.moviePoster)

            binding.root.setOnClickListener {
                onItemClick.invoke(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size
}
