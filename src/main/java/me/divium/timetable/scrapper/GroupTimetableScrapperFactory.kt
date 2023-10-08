package me.divium.timetable.scrapper

import me.divium.timetable.scrapper.exceptions.NoSuchScrapperException
import me.divium.timetable.scrapper.lib.GroupTimetableScrapper
import me.divium.timetable.scrapper.scrappers.HtmlRutMobileGroupTimetableScrapper

class GroupTimetableScrapperFactory(val a: String) {
    companion object {
        fun get(name: String, url: String): GroupTimetableScrapper {
            return when(name.lowercase()) {
                "rut" -> HtmlRutMobileGroupTimetableScrapper("https://miit.ru$url")
                else -> throw NoSuchScrapperException("Group timetable scrapper for '$name' not found. If it exists add it to this factory.")
            }
        }
    }
}