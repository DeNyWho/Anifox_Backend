package club.anifox.backend.jpa.entity.anime

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "media", schema = "anime")
data class AnimeMediaTable(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val url: String = "",
    val imageUrl: String = "",
    val playerUrl: String = "",
    val name: String = "",
    val kind: String = "",
    val hosting: String = "",
)
