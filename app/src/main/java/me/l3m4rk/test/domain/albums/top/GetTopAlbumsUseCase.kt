package me.l3m4rk.test.domain.albums.top

import io.reactivex.Observable
import me.l3m4rk.test.data.repositories.AlbumsRepository
import me.l3m4rk.test.presentation.common.ErrorMessageFactory
import me.l3m4rk.test.presentation.common.ViewState
import me.l3m4rk.test.presentation.models.AlbumVO
import timber.log.Timber
import javax.inject.Inject

interface GetTopAlbumsUseCase {

    fun getTopAlbumsByArtist(artist: String): Observable<ViewState<List<AlbumVO>>>

}

class GetTopAlbumsUseCaseImpl
@Inject constructor(
    private val repository: AlbumsRepository,
    private val messageFactory: ErrorMessageFactory
) : GetTopAlbumsUseCase {

    override fun getTopAlbumsByArtist(artist: String): Observable<ViewState<List<AlbumVO>>> {
        return repository.getTopAlbumsByArtist(artist)
            .map { ViewState.Success(it) as ViewState<List<AlbumVO>> }
            .doOnError { Timber.w(it) }
            .onErrorReturn { ViewState.Error(messageFactory.createMessage(it)) }
            .startWith(ViewState.Progress())
    }
}