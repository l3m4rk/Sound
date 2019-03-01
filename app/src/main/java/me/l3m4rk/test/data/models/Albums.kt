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

data class AlbumInfoResponse(
    val album: AlbumInfoDTO
)

data class AlbumInfoDTO(
    val name: String,
    val artist: String,
    val id: Long,
    val mbid: String,
    val url: String,
    val image: List<ImageDTO>,
    val listeners: Int,
    @SerializedName("playcount") val playCount: Int,
    val tracks: Tracks,
    val wiki: Wiki?
) {
    data class Tracks(val tracks: List<TrackDTO>)

    data class Wiki(
        val summary: String,
        val content: String
    )

}

data class TrackDTO(
    val name: String,
    val duration: Int,
    val url: String
)