package club.anifox.backend.infrastructure.persistence.jpa.entity.anime

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "anime_images", schema = "anime")
data class AnimeImagesTable(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(columnDefinition = "TEXT")
    val large: String = "",

    @Column(columnDefinition = "TEXT")
    val medium: String = "",

    @Column(columnDefinition = "TEXT", nullable = true)
    val cover: String? = null,
)
