package me.l3m4rk.test.data.mappers

import me.l3m4rk.test.data.models.ArtistDTO
import me.l3m4rk.test.presentation.models.ArtistVO
import javax.inject.Inject

class ArtistVOMapper
@Inject constructor() :
    Mapper<ArtistDTO, ArtistVO> {

    override fun transform(list: List<ArtistDTO>): List<ArtistVO> = list.map(::transform)

    override fun transform(item: ArtistDTO): ArtistVO =
        ArtistVO(
            name = item.name,
            id = item.id,
            listeners = item.listeners ?: 0,
            imageUrl = item.image?.find { it.size == "large" }?.url ?: ""
        )
}