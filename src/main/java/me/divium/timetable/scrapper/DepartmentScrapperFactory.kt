package me.divium.timetable.scrapper

import me.divium.timetable.scrapper.exceptions.NoSuchScrapperException
import me.divium.timetable.scrapper.lib.UniversityScrapper
import me.divium.timetable.scrapper.scrappers.HtmlRutUniversityScrapper

class DepartmentScrapperFactory {
    companion object {
        fun get(name: String, url: String): UniversityScrapper {
            return when(name) {
                "rut" -> HtmlRutUniversityScrapper(url)
                else -> throw NoSuchScrapperException("Scrapper '$name' not found. If it exists add it to this factory.")
            }
        }
    }
}