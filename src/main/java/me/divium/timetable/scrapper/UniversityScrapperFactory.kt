package me.divium.timetable.scrapper

import me.divium.timetable.scrapper.exceptions.NoSuchScrapperException
import me.divium.timetable.scrapper.lib.UniversityScrapper
import me.divium.timetable.scrapper.scrappers.HtmlRutUniversityScrapper

class UniversityScrapperFactory {
    companion object {
        private val uniUrls: Map<String, String> = mapOf(
            Pair("rut", "https://miit.ru/timetable")
        )
        fun get(name: String): UniversityScrapper {
            return when(name.lowercase()) {
                "rut" -> HtmlRutUniversityScrapper(uniUrls["rut"]!!)
                else -> throw NoSuchScrapperException("University scrapper for '$name' not found. If it exists add it to this factory.")
            }
        }
    }
}