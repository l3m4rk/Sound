package me.l3m4rk.test.presentation.albums.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search_artists.*
import me.l3m4rk.test.R
import me.l3m4rk.test.di.LastFmApi
import me.l3m4rk.test.presentation.common.hideKeyboard
import timber.log.Timber
import javax.inject.Inject

class SearchArtistsFragment : DaggerFragment() {

    @Inject
    lateinit var lastFmApi: LastFmApi

    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_artists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progress.visibility = GONE

        searchButton.setOnClickListener {
            testNetworkRequest()
            it.hideKeyboard()
        }

    }

    private fun testNetworkRequest() {
        disposables.clear()
        disposables += lastFmApi.searchArtists(searchInput.text.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progress.visibility = VISIBLE }
            .doOnError { progress.visibility = GONE }
            .subscribe({
                Timber.i(it.toString())
            }, {
                Timber.w(it)
            })
    }

}
