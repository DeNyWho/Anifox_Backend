package club.anifox.backend.jpa.repository.user.anime

import club.anifox.backend.jpa.entity.anime.AnimeTable
import club.anifox.backend.jpa.entity.user.UserRecentlyAnimeTable
import club.anifox.backend.jpa.entity.user.UserTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRecentlyRepository : JpaRepository<UserRecentlyAnimeTable, String> {
    fun findByUserTableAndAnime(@Param("user") user: UserTable, @Param("anime") anime: AnimeTable): Optional<UserRecentlyAnimeTable>

    fun findByUserTable(@Param("user") user: UserTable): List<UserRecentlyAnimeTable>
}
