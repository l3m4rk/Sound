package me.l3m4rk.test.data.mappers

import android.content.Context
import me.l3m4rk.test.R
import me.l3m4rk.test.data.models.AlbumInfoDTO
import me.l3m4rk.test.presentation.models.AlbumDetailsVO
import javax.inject.Inject

class AlbumDetailsVOMapper
@Inject constructor(private val context: Context) {

    fun transform(dto: AlbumInfoDTO): AlbumDetailsVO = AlbumDetailsVO(
        name = dto.name,
        artist = dto.artist,
        imageUrl = dto.image.find { it.size == "large" }?.url ?: "",
        listeners = context.getString(R.string.album_details_listeners_format, dto.listeners),
        content = dto.wiki?.content ?: context.getString(R.string.album_details_no_content),
        played = context.getString(R.string.album_details_played_format, dto.playCount)
    )

}
