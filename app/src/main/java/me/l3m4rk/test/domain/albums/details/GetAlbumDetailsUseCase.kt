package me.l3m4rk.test.domain.albums.details

import io.reactivex.Observable
import me.l3m4rk.test.data.repositories.AlbumsRepository
import me.l3m4rk.test.presentation.common.ErrorMessageFactory
import me.l3m4rk.test.presentation.common.ViewState
import me.l3m4rk.test.presentation.models.AlbumDetailsVO
import timber.log.Timber
import javax.inject.Inject

interface GetAlbumDetailsUseCase {

    fun getAlbumDetails(artist: String, album: String): Observable<ViewState<AlbumDetailsVO>>

}

class GetAlbumDetailsUseCaseImpl
@Inject constructor(
    private val repository: AlbumsRepository,
    private val messageFactory: ErrorMessageFactory
) : GetAlbumDetailsUseCase {

    override fun getAlbumDetails(artist: String, album: String): Observable<ViewState<AlbumDetailsVO>> {
        return repository.getAlbumInfo(artist, album)
            .map { ViewState.Success(it) as ViewState<AlbumDetailsVO> }
            .doOnError { Timber.i(it) }
            .onErrorReturn { ViewState.Error(messageFactory.createMessage(it)) }
            .startWith(ViewState.Progress())
    }

}