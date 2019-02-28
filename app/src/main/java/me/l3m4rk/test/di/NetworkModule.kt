package me.l3m4rk.test.di

import dagger.Module
import dagger.Provides
import me.l3m4rk.test.BuildConfig
import me.l3m4rk.test.data.api.LastFmApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://ws.audioscrobbler.com/"
const val API_KEY = "46a2da37d372e79aee9b4060a7fdd942"

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

