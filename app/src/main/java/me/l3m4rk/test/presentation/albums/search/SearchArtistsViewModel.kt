package me.l3m4rk.test.presentation.albums.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import me.l3m4rk.test.data.api.LastFmApi
import me.l3m4rk.test.presentation.common.ErrorMessageFactory
import me.l3m4rk.test.presentation.common.ViewState
import me.l3m4rk.test.presentation.models.ArtistVO
import timber.log.Timber

class SearchArtistsViewModel(
    private val lastFmApi: LastFmApi,
    private val messageFactory: ErrorMessageFactory
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _uiState = MutableLiveData<ViewState<List<ArtistVO>>>()
    val uiState: LiveData<ViewState<List<ArtistVO>>>
        get() = _uiState

    init {
        _uiState.value = ViewState.Initial()
    }

    fun searchArtists(query: String) {
        disposables.clear()
        disposables += lastFmApi.searchArtists(query)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map { it.results.matches.artist }
            .map {
                it.map { dto ->
                    ArtistVO(
                        name = dto.name,
                        id = dto.id,
                        listeners = dto.listeners ?: 0,
                        imageUrl = dto.image?.find { it.size == "large" }?.url ?: ""
                    )
                }
            }
            .map { ViewState.Success(it) as ViewState<List<ArtistVO>> }
            .doOnError { Timber.w(it) }
            .onErrorReturn { ViewState.Error(messageFactory.createMessage(it)) }
            .startWith(ViewState.Progress())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _uiState.value = it
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}