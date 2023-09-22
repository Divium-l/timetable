package me.divium.timetable.scrapper

import me.divium.timetable.scrapper.scrappers.HtmlRutMobileGroupTimetableScrapper

fun main() {
    try {
        val scrapper = HtmlRutMobileGroupTimetableScrapper("")
        scrapper.scrape()
        val timetable = scrapper.timetable
    } catch (e: ParserException) {
        println("Parsing failed with ${e.message}")
    }

}