package com.shanaka.zzz_movies.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanaka.zzz_movies.data.model.Movie
import com.shanaka.zzz_movies.data.repository.Repository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = Repository()
    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> = _searchResults

    fun search(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.searchMovies(query)
                if (response.isSuccessful) {
                    _searchResults.postValue(response.body()?.results ?: emptyList())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
