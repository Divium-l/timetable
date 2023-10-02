package me.divium.timetable.scrapper.model.timetable

import kotlinx.serialization.Serializable

@Serializable
data class SLessonInfo(
    val teacher: String? = null,
    val room: String? = null,
    val group: String? = null,
)