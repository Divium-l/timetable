package me.divium.timetable.scrapper.model.timetable

data class Day(
    val dayOfWeek: String,
    val lessons: List<Lesson>
)