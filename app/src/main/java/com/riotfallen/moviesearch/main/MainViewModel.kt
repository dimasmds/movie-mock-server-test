package com.riotfallen.moviesearch.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riotfallen.moviesearch.entity.MovieItem
import com.riotfallen.moviesearch.network.MovieDataSourceCallback
import com.riotfallen.moviesearch.network.MovieDataSourceImpl

class MainViewModel : ViewModel() {

    val observerState = MutableLiveData<MainViewModelState>()

    fun doSearch(query: String) {
        if(query.isEmpty()) {
            observerState.postValue(Error("Query must not be empty"))
            return
        }

        observerState.postValue(Search)
        MovieDataSourceImpl().search(query, object : MovieDataSourceCallback {
            override fun onResponse(movies: List<MovieItem>) {
                if(movies.isEmpty()) {
                    observerState.postValue(Empty(query))
                } else {
                    observerState.postValue(Result(movies.toMutableList()))
                }
            }

            override fun onFailed(error: String) {
                observerState.postValue(Error(error))
            }

        })
    }
}