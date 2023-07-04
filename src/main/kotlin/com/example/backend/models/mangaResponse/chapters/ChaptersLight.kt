@file:UseSerializers(LocalDateSerializer::class)
package com.example.backend.models.mangaResponse.chapters

import com.example.backend.util.LocalDateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

@Serializable
data class ChaptersLight(
    @SerialName("title")
    val title: String = "",
    val urlCode: Int = 0,
    @SerialName("date")
    val date: LocalDate = LocalDate.now(),
    val id: String
)