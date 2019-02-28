package me.l3m4rk.test.data.api

import io.reactivex.Observable
import me.l3m4rk.test.data.models.SearchResponse
import me.l3m4rk.test.di.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmApi {

    @GET("2.0/?method=artist.search&api_key=$API_KEY&format=json")
    fun searchArtists(
        @Query("artist") artist: String
    ): Observable<SearchResponse>


}