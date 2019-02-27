package me.l3m4rk.test.presentation.albums.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import me.l3m4rk.test.R
import me.l3m4rk.test.di.LastFmApi
import timber.log.Timber
import javax.inject.Inject

class SearchArtistsFragment : DaggerFragment() {

    @Inject lateinit var lastFmApi: LastFmApi

    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_artists, container, false)
    }

    override fun onStart() {
        super.onStart()

        //TODO remove it from here
        testNetworkRequest()

    }

    private fun testNetworkRequest() {
        Timber.i("On start")
        disposables += lastFmApi.searchArtists("Queen")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { }
            .subscribe({
                Timber.i(it.toString())
            }, {
                Timber.w(it)
            })
    }

}
