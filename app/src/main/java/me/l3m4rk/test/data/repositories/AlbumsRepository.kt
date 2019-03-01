package me.l3m4rk.test.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import me.l3m4rk.test.data.api.LastFmApi
import me.l3m4rk.test.data.db.Album
import me.l3m4rk.test.data.db.AlbumDatabase
import me.l3m4rk.test.data.mappers.AlbumDetailsVOMapper
import me.l3m4rk.test.data.mappers.AlbumsVOMapper
import me.l3m4rk.test.data.mappers.ArtistVOMapper
import me.l3m4rk.test.presentation.models.AlbumDetailsVO
import me.l3m4rk.test.presentation.models.AlbumVO
import me.l3m4rk.test.presentation.models.ArtistVO
import javax.inject.Inject

interface AlbumsRepository {

    fun searchArtists(query: String): Observable<List<ArtistVO>>

    fun getTopAlbumsByArtist(artist: String): Observable<List<AlbumVO>>

    fun getAlbumInfo(artist: String, album: String): Observable<AlbumDetailsVO>

    fun saveAlbum(albumDetailsVO: AlbumDetailsVO): Completable

    fun deleteAlbum(album: String): Completable

    fun getSavedAlbums(): Observable<List<AlbumVO>>
}

class AlbumsRepositoryImpl @Inject constructor(
    private val lastFmApi: LastFmApi,
    private val albumDatabase: AlbumDatabase,
    private val artistMapper: ArtistVOMapper,
    private val albumsMapper: AlbumsVOMapper,
    private val albumDetailsMapper: AlbumDetailsVOMapper

) : AlbumsRepository {
    override fun searchArtists(query: String): Observable<List<ArtistVO>> {
        return lastFmApi.searchArtists(query)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map { it.results.matches.artist }
            .map(artistMapper::transform)
    }

    override fun getTopAlbumsByArtist(artist: String): Observable<List<AlbumVO>> {
        return lastFmApi.fetchTopAlbums(artist)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map { it.topAlbums.album }
            .map(albumsMapper::transform)
    }

    override fun getAlbumInfo(artist: String, album: String): Observable<AlbumDetailsVO> {

        val fromDb = albumDatabase.albumDao()
            .getItem(album, artist)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                AlbumDetailsVO(
                    name = it.name,
                    artist = it.artist,
                    imageUrl = it.imageUrl,
                    listeners = it.listeners,
                    played = it.played,
                    content = it.content,
                    fromDb = true
                )
            }

        val fromNetwork =  lastFmApi.getAlbumInfo(artist, album)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map { it.album }
            .map(albumDetailsMapper::transform)


        return fromDb.switchIfEmpty(fromNetwork.firstElement()).toObservable()

    }

    override fun saveAlbum(albumDetailsVO: AlbumDetailsVO): Completable {
        return Completable.fromAction {
            albumDatabase.albumDao().insertAll(
                Album(
                    name = albumDetailsVO.name,
                    artist = albumDetailsVO.artist,
                    imageUrl = albumDetailsVO.imageUrl,
                    listeners = albumDetailsVO.listeners,
                    content = albumDetailsVO.content,
                    played = albumDetailsVO.played
                )
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun deleteAlbum(album: String): Completable {
        return Completable.fromAction { albumDatabase.albumDao().deleteAlbum(album) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
    }

    override fun getSavedAlbums(): Observable<List<AlbumVO>> {
        return albumDatabase.albumDao()
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            //TODO map entity -> vo
            .map {
                it.map {
                    AlbumVO(
                        name = it.name,
                        imageUrl = it.imageUrl,
                        listeners = it.listeners,
                        artist = it.artist
                    )
                }
            }
    }


}


