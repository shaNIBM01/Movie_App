package com.shanaka.zmovies.ui.search

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shanaka.zmovies.R
import com.shanaka.zmovies.databinding.FragmentSearchBinding
import com.shanaka.zmovies.ui.adapter.SearchAdapter

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SearchAdapter { selectedMovie ->
            // Prepare the bundle manually (since Safe Args is optional)
            val bundle = Bundle().apply {
                putParcelable("movie", selectedMovie)
                putParcelableArrayList("genres", ArrayList(viewModel.genres.value ?: emptyList()))
            }
            findNavController().navigate(R.id.nav_details, bundle)
        }

        binding.searchResultsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResultsRecycler.adapter = adapter

        // Auto focus and open keyboard
        binding.searchInput.requestFocus()
        binding.searchInput.postDelayed({
            val imm = context?.getSystemService(InputMethodManager::class.java)
            imm?.showSoftInput(binding.searchInput, InputMethodManager.SHOW_IMPLICIT)
        }, 100)

        // Debounced search
        binding.searchInput.doAfterTextChanged {
            val query = it.toString().trim()
            viewModel.searchMovies(query)
        }

        // Observe results
        viewModel.searchResults.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }

        viewModel.genres.observe(viewLifecycleOwner) { genreList ->
            adapter.setGenres(genreList)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}