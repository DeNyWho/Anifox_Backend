package club.anifox.backend.jpa.entity.anime.episodes

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "translation", schema = "anime")
data class AnimeTranslationTable(
    @Id
    val id: Int = 0,
    val title: String = "",
    val voice: String = "",
)
