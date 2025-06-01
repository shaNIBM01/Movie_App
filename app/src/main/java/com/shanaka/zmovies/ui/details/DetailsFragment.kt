package com.shanaka.zmovies.ui.details

import android.os.Build
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.shanaka.zmovies.R
import com.shanaka.zmovies.data.model.Genre
import com.shanaka.zmovies.data.model.Movie
import com.shanaka.zmovies.databinding.FragmentDetailsBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private var movie: Movie? = null
    private var genreList: List<Genre>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable("movie")
        genreList = arguments?.getParcelableArrayList("genres")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movie?.let { movie ->
            binding.movieTitle.text = movie.title
            binding.movieOverview.text = movie.overview

            // Format date
            binding.movieRelease.text = "Release: ${formatDate(movie.releaseDate)}"

            // Format rating with 1 decimal place
            binding.movieRating.text = "Rating: ${String.format("%.1f", movie.voteAverage)}"

            // Map genre IDs to genre names
            val genresFormatted = movie.genreIds.mapNotNull { id ->
                genreList?.find { it.id == id }?.name
            }.joinToString(", ")
            binding.movieGenres.text = "Genres: $genresFormatted"

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .placeholder(R.drawable.ic_no_image)
                .error(R.drawable.ic_no_image)
                .into(binding.moviePoster)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDate(dateString: String): String {
        return try {
            val parsedDate = LocalDate.parse(dateString)
            parsedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.US))
        } catch (e: Exception) {
            dateString
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}