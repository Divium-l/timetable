package me.divium.timetable.scrapper

import me.divium.timetable.scrapper.exceptions.NoSuchScrapperException
import me.divium.timetable.scrapper.lib.GroupTimetableScrapper
import me.divium.timetable.scrapper.scrappers.HtmlRutMobileGroupTimetableScrapper

class GroupTimetableScrapperFactory(val a: String) {
    companion object {
        fun get(name: String, url: String): GroupTimetableScrapper {
            return when(name) {
                "rut" -> HtmlRutMobileGroupTimetableScrapper(url)
                else -> throw NoSuchScrapperException("Scrapper '$name' not found. If it exists add it to this factory.")
            }
        }
    }
}