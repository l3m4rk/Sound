package me.l3m4rk.test.presentation.albums.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import me.l3m4rk.test.domain.albums.details.GetAlbumDetailsUseCase
import me.l3m4rk.test.presentation.common.ViewState
import me.l3m4rk.test.presentation.models.AlbumDetailsVO
import timber.log.Timber

class AlbumDetailsViewModel(
    private val useCase: GetAlbumDetailsUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _uiState = MutableLiveData<ViewState<AlbumDetailsVO>>()
    val uiState: LiveData<ViewState<AlbumDetailsVO>>
        get() = _uiState

    fun loadAlbumDetails(artist: String, album: String) {
        disposables += useCase.getAlbumDetails(artist, album)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _uiState.value = it }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun saveAlbum() {
        //TODO save album to DB
        Timber.i("Save item")
    }

}

