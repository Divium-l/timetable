package me.divium.timetable.scrapper.model.timetable

data class SDay(
    val dayOfWeek: String,
    val sLessons: List<SLesson>
)