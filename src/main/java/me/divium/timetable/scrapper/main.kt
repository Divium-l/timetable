package me.divium.timetable.scrapper

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.divium.timetable.scrapper.exceptions.ParserException
import me.divium.timetable.scrapper.model.timetable.SLessonInfo
import me.divium.timetable.scrapper.scrappers.HtmlRutMobileGroupTimetableScrapper

fun main() {
    try {
        val scrapper = HtmlRutMobileGroupTimetableScrapper("https://miit.ru/timetable/193569")
        scrapper.scrape()
        val result = scrapper.getResult()
    } catch (e: ParserException) {
        println("Parsing failed with ${e.message}")
    }
}