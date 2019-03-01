package me.l3m4rk.test.data.db

import androidx.room.*
import io.reactivex.Observable

@Database(entities = [Album::class], version = 1)
abstract class AlbumDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao

}

@Entity(primaryKeys = ["album_name", "artist_name"])
data class Album(
    @ColumnInfo(name = "album_name") var name: String,
    @ColumnInfo(name = "artist_name") var artist: String,
    @ColumnInfo(name = "image_url") var imageUrl: String,
    @ColumnInfo(name = "listeners") var listeners: String,
    @ColumnInfo(name = "played") var played: String,
    @ColumnInfo(name = "content") var content: String
)

@Dao
interface AlbumDao {

    @Query("SELECT * FROM album")
    fun getAll(): Observable<List<Album>>

    @Insert
    fun insertAll(vararg albums: Album)

    @Query("SELECT * FROM album WHERE album_name = :album AND artist_name = :artist")
    fun getItem(album: String, artist: String): Observable<Album>
}