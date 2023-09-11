package club.anifox.backend.infrastructure.persistence.jpa.repository.anime

import club.anifox.backend.infrastructure.persistence.jpa.entity.anime.AnimeGenreTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AnimeGenreRepository : JpaRepository<AnimeGenreTable, String> {
    @Query("Select g from AnimeGenreTable g where :genre = g.genre")
    fun findByGenre(genre: String): Optional<AnimeGenreTable>
}
