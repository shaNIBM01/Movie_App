package com.shanaka.zzz_movies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.shanaka.zzz_movies.R
import com.shanaka.zzz_movies.ui.adapter.MovieAdapter

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private val apiKey = "  389556a5ea0ca785735dd13e7610c87b"

    private lateinit var btnAll: MaterialButton
    private lateinit var btnAction: MaterialButton
    private lateinit var btnComedy: MaterialButton
    private lateinit var btnDrama: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewMovies)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize buttons
        btnAll = view.findViewById(R.id.btnAll)
        btnAction = view.findViewById(R.id.btnAction)
        btnComedy = view.findViewById(R.id.btnComedy)
        btnDrama = view.findViewById(R.id.btnDrama)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe movies
        viewModel.movies.observe(viewLifecycleOwner, Observer { movieList ->
            recyclerView.adapter = MovieAdapter(movieList)
        })

        // Load all movies
        viewModel.loadMovies(apiKey)

        // Set default selection to "All"
        selectButton(btnAll)
        viewModel.filterByGenre("All")

        // Set filter listeners
        btnAll.setOnClickListener {
            selectButton(it)
            viewModel.filterByGenre("All")
        }

        btnAction.setOnClickListener {
            selectButton(it)
            viewModel.filterByGenre("Action")
        }

        btnComedy.setOnClickListener {
            selectButton(it)
            viewModel.filterByGenre("Comedy")
        }

        btnDrama.setOnClickListener {
            selectButton(it)
            viewModel.filterByGenre("Drama")
        }
    }

    private fun selectButton(selected: View) {
        val buttons = listOf(btnAll, btnAction, btnComedy, btnDrama)
        buttons.forEach { it.isChecked = false }
        (selected as? MaterialButton)?.isChecked = true
    }
}
