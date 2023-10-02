package me.divium.timetable.scrapper.model.timetable

import kotlinx.serialization.Serializable

@Serializable
data class SDay(
    val dayOfWeek: String,
    val sLessons: List<SLesson>
)