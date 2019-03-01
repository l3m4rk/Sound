package me.l3m4rk.test.domain.albums.saved

import io.reactivex.Observable
import me.l3m4rk.test.data.repositories.AlbumsRepository
import me.l3m4rk.test.presentation.common.ErrorMessageFactory
import me.l3m4rk.test.presentation.common.ViewState
import me.l3m4rk.test.presentation.models.AlbumVO
import timber.log.Timber
import javax.inject.Inject

interface SavedAlbumsUseCase {

    fun getAlbums(): Observable<ViewState<List<AlbumVO>>>

}

class SavedAlbumsUseCaseImpl @Inject constructor(
    private val repository: AlbumsRepository,
    private val messageFactory: ErrorMessageFactory
) : SavedAlbumsUseCase {

    override fun getAlbums(): Observable<ViewState<List<AlbumVO>>> {
        return repository.getSavedAlbums()
            .map { ViewState.Success(it) as ViewState<List<AlbumVO>> }
            .doOnError { Timber.i(it) }
            .onErrorReturn { ViewState.Error(messageFactory.createMessage(it)) }
            .startWith(ViewState.Progress())
    }

}