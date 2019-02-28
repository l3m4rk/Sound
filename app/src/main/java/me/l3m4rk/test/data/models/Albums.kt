package me.l3m4rk.test.data.models

import com.google.gson.annotations.SerializedName


data class TopAlbumsResponse(
    @SerializedName("topalbums") val topAlbums: TopAlbums
)

data class TopAlbums(val album: List<AlbumDTO>)

data class AlbumDTO(
    val name: String,
    @SerializedName("playcount") val playCount: Long,
    @SerializedName("mbid") val id: String,
    val url: String,
    val artist: ArtistDTO,
    val image: List<ImageDTO>
)
