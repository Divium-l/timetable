package me.divium.timetable.scrapper.model

data class Day(
    val dayOfWeek: String,
    val lessons: List<Lesson>
)