package club.anifox.backend.service.anime

import club.anifox.backend.domain.enums.anime.AnimeOrder
import club.anifox.backend.domain.enums.anime.AnimeSeason
import club.anifox.backend.domain.enums.anime.AnimeStatus
import club.anifox.backend.domain.enums.anime.AnimeType
import club.anifox.backend.domain.model.anime.light.AnimeLight
import club.anifox.backend.domain.repository.anime.AnimeRepository
import club.anifox.backend.service.anime.components.AnimeSearchComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AnimeService : AnimeRepository {

    @Autowired
    private lateinit var animeSearchComponent: AnimeSearchComponent

    override fun getAnime(
        pageNum: Int,
        pageSize: Int,
        genres: List<String>?,
        status: AnimeStatus?,
        order: AnimeOrder?,
        searchQuery: String?,
        season: AnimeSeason?,
        ratingMpa: String?,
        minimalAge: Int?,
        type: AnimeType?,
        year: List<Int>?,
        translations: List<String>?,
        studio: String?,
    ): List<AnimeLight> {
        return animeSearchComponent.getAnime(pageNum, pageSize, genres, status, order, searchQuery, season, ratingMpa, minimalAge, type, year, translations, studio)
    }
}