package com.shanaka.zmovies.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.shanaka.zmovies.databinding.FragmentHomeBinding
import com.shanaka.zmovies.ui.adapter.MovieAdapter

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MovieAdapter { selectedMovie ->
            // Prepare the bundle manually (since Safe Args is optional)
            val bundle = Bundle().apply {
                putParcelable("movie", selectedMovie)
                putParcelableArrayList("genres", ArrayList(viewModel.genres.value ?: emptyList()))
            }
            findNavController().navigate(com.shanaka.zmovies.R.id.nav_details, bundle)
        }
        binding.movieRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.movieRecyclerView.adapter = adapter

        // Observe ViewModel
        viewModel.genres.observe(viewLifecycleOwner) { genreList ->
            adapter.setGenres(genreList)
        }

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }


        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            binding.movieRecyclerView.visibility = if (loading) View.INVISIBLE else View.VISIBLE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            // Optionally show error message
        }

        // Chip selection
        binding.categoryChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            viewModel.filterByGenre(chip.text.toString())
        }

        viewModel.loadLatestMovies()
    }
}