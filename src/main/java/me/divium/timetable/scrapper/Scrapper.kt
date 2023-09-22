package me.divium.timetable.scrapper

import org.jsoup.nodes.Document

interface Scrapper<Result> {
    fun scrape()
    fun getResult(): Result
}