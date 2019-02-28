package me.l3m4rk.test.data.models

import com.google.gson.annotations.SerializedName

data class SearchResponse(val results: Results)

data class Results(
    val totalResults: Int,
    val itemPerPage: Int,
    @SerializedName("artistmatches") val matches: Matches
) {
    data class Matches(
        val artist: List<ArtistDTO>
    )
}

data class ArtistDTO(
    val name: String,
    val listeners: Long,
    @SerializedName("mbid") val id: String,
    val url: String,
    val image: List<Image>
) {
    data class Image(
        @SerializedName("#text") val url: String,
        val size: String
    )
}
