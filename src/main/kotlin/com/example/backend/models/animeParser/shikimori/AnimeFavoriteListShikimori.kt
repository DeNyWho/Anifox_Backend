package com.example.backend.models.animeParser.shikimori

import kotlinx.serialization.Serializable

@Serializable
data class AnimeFavoriteListShikimori(
    val name: String,
    val value: Int
)