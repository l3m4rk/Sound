package me.l3m4rk.test.presentation.models

data class ArtistVO(
    val id: String,
    val name: String,
    val listeners: Long,
    val imageUrl: String
)

data class AlbumVO(
    val name: String,
    val listeners: String,
    val imageUrl: String
)