package club.anifox.backend.domain.repository.user

import club.anifox.backend.domain.enums.user.StatusFavourite
import club.anifox.backend.domain.model.anime.light.AnimeLight
import club.anifox.backend.domain.model.anime.recently.AnimeRecently
import club.anifox.backend.domain.model.anime.recently.AnimeRecentlyRequest
import jakarta.servlet.http.HttpServletResponse

interface UserRepository {
    fun setAnimeRating(token: String, url: String, rating: Int, response: HttpServletResponse)
    fun getRecentlyAnimeByUrl(token: String, url: String): AnimeRecently
    fun getRecentlyAnimeAll(token: String, page: Int, limit: Int): List<AnimeRecently>
    fun addToFavoritesAnime(token: String, url: String, status: StatusFavourite, episodeNumber: Int?, response: HttpServletResponse)
    fun getFavoritesAnimeByStatus(token: String, status: StatusFavourite, page: Int, limit: Int): List<AnimeLight>
    fun addToRecentlyAnime(token: String, url: String, recently: AnimeRecentlyRequest, response: HttpServletResponse)
    fun getRecommendations(token: String, page: Int, limit: Int): List<AnimeLight>
    fun updatePreferredGenres(token: String, genres: List<String>, response: HttpServletResponse)
}
