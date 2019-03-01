package me.l3m4rk.test.domain.albums.details

import io.reactivex.Completable
import me.l3m4rk.test.data.repositories.AlbumsRepository
import me.l3m4rk.test.presentation.common.ErrorMessageFactory
import me.l3m4rk.test.presentation.models.AlbumDetailsVO
import timber.log.Timber
import javax.inject.Inject

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