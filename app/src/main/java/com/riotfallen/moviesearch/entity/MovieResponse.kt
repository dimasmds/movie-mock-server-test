package com.riotfallen.moviesearch.entity

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("results") val movies: List<MovieItem> = listOf()
)