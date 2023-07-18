package com.example.backend

import com.example.backend.service.anime.AnimeService
import com.example.backend.service.image.ImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.net.URL
import java.util.concurrent.TimeUnit

@Component
class ScheduleTasks {

	@Autowired
	private lateinit var animeService: AnimeService

	@Scheduled(fixedRate = 12, timeUnit = TimeUnit.HOURS)
	fun refreshData(){
		animeService.addDataToDB("610, 609, 735, 643, 559, 739, 767, 825, 933, 557, 794, 1002")
	}
}

@SpringBootApplication
@EnableScheduling
@EnableCaching
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
