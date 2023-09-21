package me.divium.timetable.scrapper

import me.divium.timetable.scrapper.scrappers.HtmlRutMobileScrapper

fun main() {
    val scrapper = HtmlRutMobileScrapper("")
    scrapper.scrape()
    val timetable = scrapper.timetable
}