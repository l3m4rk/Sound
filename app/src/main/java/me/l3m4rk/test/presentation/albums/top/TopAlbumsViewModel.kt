package me.l3m4rk.test.presentation.albums.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import me.l3m4rk.test.domain.albums.top.GetTopAlbumsUseCase
import me.l3m4rk.test.presentation.common.ViewState
import me.l3m4rk.test.presentation.models.AlbumVO

class TopAlbumsViewModel(
    private val useCase: GetTopAlbumsUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _screenState = MutableLiveData<ViewState<List<AlbumVO>>>()
    val screenState: LiveData<ViewState<List<AlbumVO>>>
        get() = _screenState

    fun loadTopAlbums(artist: String) {
        disposables += useCase.getTopAlbumsByArtist(artist)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _screenState.value = it }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}