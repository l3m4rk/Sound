package me.l3m4rk.test.di

import com.google.gson.annotations.SerializedName
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import me.l3m4rk.test.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://ws.audioscrobbler.com/"
private const val API_KEY = "46a2da37d372e79aee9b4060a7fdd942"

@Module
class NetworkModule {

    @Provides
    fun provideLastFmApi(): LastFmApi {
        val logger = HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        val client = OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(logger)
                }
            }.build()

        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build()
            .create(LastFmApi::class.java)
    }

}

interface LastFmApi {

    @GET("2.0/?method=artist.search&api_key=$API_KEY&format=json")
    fun searchArtists(
        @Query("artist") artist: String
    ): Observable<SearchResponse>


}

data class SearchResponse(val results: Results)

data class Results(
    val totalResults: Int,
    val itemPerPage: Int,
    @SerializedName("artistmatches") val matches: Matches
) {
    data class Matches(
        val artist: List<ArtistDTO>
    )
}

data class ArtistDTO(
    val name: String,
    val listeners: Long,
    @SerializedName("mbid") val id: String,
    val url: String,
    val image: List<Image>
) {
    data class Image(
        @SerializedName("#text") val url: String,
        val size: String
    )
}


