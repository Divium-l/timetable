package me.divium.timetable.scrapper

import me.divium.timetable.scrapper.exceptions.NoSuchScrapperException
import me.divium.timetable.scrapper.lib.DepartmentScrapper
import me.divium.timetable.scrapper.scrappers.HtmlRutDepartmentScrapper

class DepartmentScrapperFactory {
    companion object {
        fun get(name: String, url: String): DepartmentScrapper {
            return when(name) {
                "rut" -> HtmlRutDepartmentScrapper(url)
                else -> throw NoSuchScrapperException("Scrapper '$name' not found. If it exists add it to this factory.")
            }
        }
    }
}