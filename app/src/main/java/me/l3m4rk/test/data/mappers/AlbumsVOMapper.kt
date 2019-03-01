package me.l3m4rk.test.data.mappers

import me.l3m4rk.test.data.models.AlbumDTO
import me.l3m4rk.test.presentation.albums.top.AlbumVO
import javax.inject.Inject

class AlbumsVOMapper
@Inject constructor() :
    Mapper<AlbumDTO, AlbumVO> {
    override fun transform(list: List<AlbumDTO>): List<AlbumVO> =
        list.filter { it.name != "(null)" }
            .map(this::transform)

    override fun transform(item: AlbumDTO): AlbumVO =
        AlbumVO(
            name = item.name,
            listeners = item.playCount.toString(),
            imageUrl = item.image.find { it.size == "large" }?.url ?: ""
        )
}