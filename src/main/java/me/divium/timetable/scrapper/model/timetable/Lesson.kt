package me.divium.timetable.scrapper.model.timetable

data class Lesson (
    val name: String,
    val type: String,
    val number: Byte,
    val time: String,
    val info: LessonInfo,
)