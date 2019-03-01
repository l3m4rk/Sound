package me.l3m4rk.test.presentation.albums.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import me.l3m4rk.test.domain.albums.search.SearchArtistsUseCase
import me.l3m4rk.test.presentation.common.ViewState
import me.l3m4rk.test.presentation.models.ArtistVO

class SearchArtistsViewModel(
    private val useCase: SearchArtistsUseCase
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
        disposables += useCase.searchArtists(query)
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