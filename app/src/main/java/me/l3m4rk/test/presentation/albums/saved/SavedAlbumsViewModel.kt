package me.l3m4rk.test.presentation.albums.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import me.l3m4rk.test.domain.albums.saved.SavedAlbumsUseCase
import me.l3m4rk.test.presentation.common.ViewState
import me.l3m4rk.test.presentation.models.AlbumVO

class SavedAlbumsViewModel(
    private val savedAlbumsUseCase: SavedAlbumsUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _uiState = MutableLiveData<ViewState<List<AlbumVO>>>()
    val uiState: LiveData<ViewState<List<AlbumVO>>>
        get() = _uiState


    fun loadAlbums() {
        disposables += savedAlbumsUseCase.getAlbums()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _uiState.value = it }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}