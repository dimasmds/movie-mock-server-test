package com.riotfallen.moviesearch.network

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.riotfallen.moviesearch.entity.MovieResponse

class MovieDataSourceImpl {
    fun search(query: String, movieDataSourceCallback: MovieDataSourceCallback) {
        AndroidNetworking.get("${MovieDataSource.baseUrl}search/movie?apiKey=b650046bf640e7bf7054093854b8d02a&query=$query")
            .setTag(MovieDataSourceImpl::class.java)
            .setPriority(Priority.HIGH)
            .build()
            .getAsObject(MovieResponse::class.java, object : ParsedRequestListener<MovieResponse> {
                override fun onResponse(response: MovieResponse) {
                    movieDataSourceCallback.onResponse(response.movies)
                }

                override fun onError(anError: ANError) {
                    Log.d("ERROR", "onError: ${anError.errorBody}", anError)
                    movieDataSourceCallback.onFailed(anError.errorDetail)
                }

            })
    }
}