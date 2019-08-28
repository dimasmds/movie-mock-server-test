package com.riotfallen.moviesearch.network

import com.riotfallen.moviesearch.entity.MovieItem

interface MovieDataSourceCallback {
    fun onResponse(movies: List<MovieItem>)
    fun onFailed(error: String)
}