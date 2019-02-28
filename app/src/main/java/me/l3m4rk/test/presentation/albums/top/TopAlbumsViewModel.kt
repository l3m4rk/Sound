package me.l3m4rk.test.presentation.albums.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import me.l3m4rk.test.data.api.LastFmApi
import me.l3m4rk.test.presentation.common.ViewState
import timber.log.Timber

class TopAlbumsViewModel(private val api: LastFmApi) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _screenState = MutableLiveData<ViewState<List<AlbumVO>>>()
    val screenState: LiveData<ViewState<List<AlbumVO>>>
        get() = _screenState

    fun loadTopAlbums(artist: String) {
        disposables += api.fetchTopAlbums(artist)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map { it.topAlbums.album }
            .map {
                it.map { dto ->
                    AlbumVO(
                        name = dto.name,
                        listeners = dto.playCount.toString(),
                        imageUrl = dto.image.find { it.size == "large" }?.url ?: ""
                    )
                }
            }
            .map { ViewState.Success(it) as ViewState<List<AlbumVO>> }
            .onErrorReturn {
                Timber.w(it)
                ViewState.Error(it.localizedMessage)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _screenState.value = it
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}

data class AlbumVO(
    val name: String,
    val listeners: String,
    val imageUrl: String
)
