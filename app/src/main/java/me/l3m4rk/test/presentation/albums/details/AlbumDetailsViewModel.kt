package me.l3m4rk.test.presentation.albums.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import me.l3m4rk.test.data.repositories.AlbumsRepository
import me.l3m4rk.test.domain.albums.details.GetAlbumDetailsUseCase
import me.l3m4rk.test.presentation.common.ErrorMessageFactory
import me.l3m4rk.test.presentation.common.ViewState
import me.l3m4rk.test.presentation.models.AlbumDetailsVO
import timber.log.Timber
import javax.inject.Inject

class AlbumDetailsViewModel(
    private val saveItemUseCase: SaveAlbumUseCase,
    private val useCase: GetAlbumDetailsUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _uiState = MutableLiveData<ViewState<AlbumDetailsVO>>()
    val uiState: LiveData<ViewState<AlbumDetailsVO>>
        get() = _uiState

    private var albumDetailsVO: AlbumDetailsVO? = null

    fun loadAlbumDetails(artist: String, album: String) {
        disposables += useCase.getAlbumDetails(artist, album)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _uiState.value = it
                if (it is ViewState.Success) {
                    albumDetailsVO = it.data
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun saveAlbum() {
        albumDetailsVO?.also {
            disposables += saveItemUseCase.saveAlbum(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _uiState.value = ViewState.Info("Saved!")
                }
        }
    }

}

interface SaveAlbumUseCase {

    fun saveAlbum(albumDetailsVO: AlbumDetailsVO): Completable

}

class SaveAlbumUseCaseImpl @Inject constructor(
    private val repository: AlbumsRepository,
    private val messageFactory: ErrorMessageFactory
) : SaveAlbumUseCase {

    override fun saveAlbum(albumDetailsVO: AlbumDetailsVO): Completable {
        return repository.saveAlbum(albumDetailsVO).doOnError {
            Timber.w(it)
        }
    }
}

