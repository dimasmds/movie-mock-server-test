package com.riotfallen.moviesearch.main

import com.riotfallen.moviesearch.entity.MovieItem

sealed class MainViewModelState

object Search: MainViewModelState()
data class Empty(val query: String) : MainViewModelState()
data class Result(val movies: MutableList<MovieItem>) : MainViewModelState()
data class Error(val message: String) : MainViewModelState()