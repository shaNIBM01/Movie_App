package com.shanaka.zmovies.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanaka.zmovies.data.model.Genre
import com.shanaka.zmovies.data.model.Movie
import com.shanaka.zmovies.data.repository.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = MovieRepository()
    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> = _searchResults

    private var searchJob: Job? = null

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> = _genres

    init {
        viewModelScope.launch {
            _genres.value = repository.getGenres()
        }
    }
    fun searchMovies(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300) // Debounce to reduce API calls
            if (query.isNotBlank()) {
                try {
                    val response = repository.searchMovies(query)
                    if (response.isSuccessful) {
                        _searchResults.value = response.body()?.results ?: emptyList()
                    }
                } catch (e: Exception) {
                    // Optionally handle error
                }
            } else {
                _searchResults.value = emptyList()
            }
        }
    }
}