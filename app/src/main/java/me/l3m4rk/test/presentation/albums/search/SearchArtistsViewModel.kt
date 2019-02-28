package me.l3m4rk.test.presentation.albums.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import me.l3m4rk.test.di.LastFmApi
import me.l3m4rk.test.presentation.common.ViewState
import timber.log.Timber

class SearchArtistsViewModel(
    private val lastFmApi: LastFmApi
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _uiState = MutableLiveData<ViewState>()
    val uiState: LiveData<ViewState>
        get() = _uiState

    fun searchArtists(query: String) {
        disposables.clear()
        disposables += lastFmApi.searchArtists(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { progress.visibility = View.VISIBLE }
//            .doOnError { progress.visibility = View.GONE }
            .subscribe({
                Timber.i(it.toString())
            }, {
                Timber.w(it)
            })

    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}