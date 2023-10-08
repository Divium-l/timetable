package me.divium.timetable.scrapper.model.timetable

import kotlinx.serialization.Serializable

@Serializable
data class SLesson (
    val name: String,
    val type: String,
    val number: Byte,
    val time: String,
    val info: SLessonInfo,
)