package me.divium.timetable.scrapper

import me.divium.timetable.scrapper.scrappers.HtmlRutGroupScrapper
import me.divium.timetable.scrapper.scrappers.HtmlRutMobileGroupTimetableScrapper

fun main() {
    try {
//        val scrapper = HtmlRutMobileGroupTimetableScrapper("")
//        scrapper.scrape()
//        val timetable = scrapper.timetable
        val scrapper = HtmlRutGroupScrapper("https://miit.ru/timetable")
        scrapper.scrape()
        val result = scrapper.getResult()
        println("done")
    } catch (e: ParserException) {
        println("Parsing failed with ${e.message}")
    }
}