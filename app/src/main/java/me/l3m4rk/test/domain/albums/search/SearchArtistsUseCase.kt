package me.l3m4rk.test.domain.albums.search

import io.reactivex.Observable
import me.l3m4rk.test.data.repositories.AlbumsRepository
import me.l3m4rk.test.presentation.common.ErrorMessageFactory
import me.l3m4rk.test.presentation.common.ViewState
import me.l3m4rk.test.presentation.models.ArtistVO
import timber.log.Timber
import javax.inject.Inject

interface SearchArtistsUseCase {

    fun searchArtists(query: String): Observable<ViewState<List<ArtistVO>>>

}

class SearchArtistUseCaseImpl
@Inject constructor(
    private val repository: AlbumsRepository,
    private val messageFactory: ErrorMessageFactory
) : SearchArtistsUseCase {

    override fun searchArtists(query: String): Observable<ViewState<List<ArtistVO>>> {
        return repository.searchArtists(query)
            .map { ViewState.Success(it) as ViewState<List<ArtistVO>> }
            .doOnError { Timber.w(it) }
            .onErrorReturn { ViewState.Error(messageFactory.createMessage(it)) }
            .startWith(ViewState.Progress())
    }

}