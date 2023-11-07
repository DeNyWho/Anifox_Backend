package club.anifox.backend.service.anime.components

import club.anifox.backend.jpa.entity.anime.AnimeEpisodeTable
import club.anifox.backend.jpa.entity.anime.AnimeEpisodeTranslationCountTable
import club.anifox.backend.jpa.entity.anime.AnimeErrorParserTable
import club.anifox.backend.jpa.entity.anime.AnimeGenreTable
import club.anifox.backend.jpa.entity.anime.AnimeIdsTable
import club.anifox.backend.jpa.entity.anime.AnimeMediaTable
import club.anifox.backend.jpa.entity.anime.AnimeMusicTable
import club.anifox.backend.jpa.entity.anime.AnimeRatingTable
import club.anifox.backend.jpa.entity.anime.AnimeRelatedTable
import club.anifox.backend.jpa.entity.anime.AnimeStudioTable
import club.anifox.backend.jpa.entity.anime.AnimeTable
import club.anifox.backend.jpa.entity.anime.AnimeTranslationTable
import club.anifox.backend.jpa.entity.user.UserFavoriteAnimeTable
import club.anifox.backend.jpa.repository.anime.AnimeErrorParserRepository
import club.anifox.backend.jpa.repository.anime.AnimeRepository
import club.anifox.backend.jpa.repository.anime.AnimeTranslationCountRepository
import club.anifox.backend.jpa.repository.anime.AnimeTranslationRepository
import club.anifox.backend.service.anime.components.episodes.EpisodesComponent
import club.anifox.backend.service.anime.components.kodik.KodikComponent
import club.anifox.backend.service.anime.components.shikimori.AnimeShikimoriComponent
import io.ktor.client.*
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.JoinType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Component
class AnimeUpdateComponent {

    @Autowired
    private lateinit var animeErrorParserRepository: AnimeErrorParserRepository

    @Autowired
    private lateinit var animeTranslationRepository: AnimeTranslationRepository

    @Autowired
    private lateinit var animeRepository: AnimeRepository

    @Autowired
    private lateinit var animeTranslationCountRepository: AnimeTranslationCountRepository

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var kodikComponent: KodikComponent

    @Autowired
    private lateinit var episodesComponent: EpisodesComponent

    @Autowired
    private lateinit var shikimoriComponent: AnimeShikimoriComponent

    fun update() {
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(AnimeTable::class.java)

        val root = criteriaQuery.from(AnimeTable::class.java)
        root.fetch<AnimeRelatedTable, Any>("related", JoinType.LEFT)
        root.fetch<AnimeEpisodeTable, Any>("episodes", JoinType.LEFT)
        root.fetch<AnimeEpisodeTranslationCountTable, Any>("translationsCountEpisodes", JoinType.LEFT)
        root.fetch<AnimeIdsTable, Any>("ids", JoinType.LEFT)
        root.fetch<AnimeMusicTable, Any>("music", JoinType.LEFT)
        root.fetch<AnimeTranslationTable, Any>("translations", JoinType.LEFT)
        root.fetch<AnimeGenreTable, Any>("genres", JoinType.LEFT)
        root.fetch<AnimeMediaTable, Any>("media", JoinType.LEFT)
        root.fetch<AnimeStudioTable, Any>("studios", JoinType.LEFT)
        root.fetch<UserFavoriteAnimeTable, Any>("favorites", JoinType.LEFT)
        root.fetch<AnimeRatingTable, Any>("rating", JoinType.LEFT)

        criteriaQuery.select(root)

        val query = entityManager.createQuery(criteriaQuery)
        val animeList = query.resultList

        animeList.forEach Loop@{ anime ->
            try {
                val shikimori = shikimoriComponent.checkShikimori("${anime.shikimoriId}")
                val episodesReady = mutableListOf<AnimeEpisodeTable>()

                episodesReady.addAll(episodesComponent.fetchEpisodes(shikimoriId = anime.shikimoriId.toString(), kitsuId = anime.ids.kitsu.toString(), type = anime.type, urlLinking = anime.url, defaultImage = anime.images.medium))

                val translationsAll = animeTranslationRepository.findAll()

                val translationsCountMap = episodesReady
                    .flatMap { it.translations }
                    .groupBy { it.translation.id }
                    .map { (id, translations) ->
                        AnimeEpisodeTranslationCountTable(
                            translation = translationsAll.find { it.id == id }!!,
                            countEpisodes = translations.size,
                        )
                    }

                val translationsCountReady = animeTranslationCountRepository.saveAll(translationsCountMap)

                val translations = translationsCountReady.map { it.translation }

                val formatterUpdated = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                    .withZone(ZoneId.of("Europe/Moscow"))

                if (shikimori != null) {
                    anime.nextEpisode = if (shikimori.nextEpisodeAt != null) {
                        LocalDateTime.parse(shikimori.nextEpisodeAt, formatterUpdated)
                    } else {
                        null
                    }
                }
                anime.addEpisodesAll(episodesReady)
                anime.addTranslation(translations)
                anime.addTranslationCount(translationsCountReady)
                animeRepository.saveAndFlush(anime)
            } catch (e: Exception) {
                e.stackTrace.forEach {
                    println(it)
                }
                animeErrorParserRepository.save(
                    AnimeErrorParserTable(
                        message = e.message,
                        cause = "UPDATE",
                        shikimoriId = anime.shikimoriId,
                    ),
                )
                return@Loop
            }
        }
    }
}
