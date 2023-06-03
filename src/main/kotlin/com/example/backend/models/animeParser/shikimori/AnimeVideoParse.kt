package com.example.backend.models.animeParser.shikimori

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeVideoParse(
    @SerialName("url")
    val url: String = "",
    @SerialName("image_url")
    val imageUrl: String = "",
    @SerialName("player_url")
    val playerUrl: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("kind")
    val kind: String = "",
    @SerialName("hosting")
    val hosting: String = ""
)