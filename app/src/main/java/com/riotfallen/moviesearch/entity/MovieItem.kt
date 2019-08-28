package com.riotfallen.moviesearch.entity

import com.google.gson.annotations.SerializedName

data class MovieItem(
    @field:SerializedName("title") val title: String? = null,
    @field:SerializedName("backdrop_path") val backdropPath: String? = null,
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("overview") val overview: String? = null
)