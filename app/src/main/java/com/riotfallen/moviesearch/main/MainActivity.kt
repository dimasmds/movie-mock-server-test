package com.riotfallen.moviesearch.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.riotfallen.moviesearch.R
import com.riotfallen.moviesearch.gone
import com.riotfallen.moviesearch.visible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Observer<MainViewModelState> {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.observerState.observe(this, this)

        mainAdapter = MainAdapter()
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mainAdapter

        searchButton.setOnClickListener {
            mainViewModel.doSearch(searchView.text.toString())
        }
    }

    override fun onChanged(state: MainViewModelState?) {
        when(state) {
            is Search -> {
                progressBar.visible()
                errorMessage.gone()
                mainAdapter.setMovies(mutableListOf())
            }

            is Empty -> {
                progressBar.gone()
                errorMessage.visible()
                errorMessage.text = String.format(getString(R.string.empty_message), state.query)
            }

            is Result -> {
                progressBar.gone()
                mainAdapter.setMovies(state.movies)
            }

            is Error -> {
                progressBar.gone()
                errorMessage.visible()
                errorMessage.text  = String.format(getString(R.string.error_message), state.message)
            }
        }
    }
}
