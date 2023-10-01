package me.divium.timetable.scrapper.model.timetable

data class SLesson (
    val name: String,
    val type: String,
    val number: Byte,
    val time: String,
    val info: SLessonInfo,
)