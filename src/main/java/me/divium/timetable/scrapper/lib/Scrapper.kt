package me.divium.timetable.scrapper.lib

interface Scrapper<Result> {
    fun scrape()
    fun getResult(): Result
}