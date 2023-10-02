package me.divium.timetable.scrapper

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.divium.timetable.scrapper.exceptions.ParserException
import me.divium.timetable.scrapper.model.timetable.SLessonInfo

fun main() {
    try {
//        val scrapper = HtmlRutMobileGroupTimetableScrapper("")
//        scrapper.scrape()
//        val timetable = scrapper.timetable
//        val scrapper = HtmlRutDepartmentScrapper("https://miit.ru/timetable")
//        scrapper.scrape()
//        val result = scrapper.getResult()
        val info = SLessonInfo("teacher name", "room number", "group name")
        val json = Json.encodeToString(info)
        println(json)
    } catch (e: ParserException) {
        println("Parsing failed with ${e.message}")
    }
}