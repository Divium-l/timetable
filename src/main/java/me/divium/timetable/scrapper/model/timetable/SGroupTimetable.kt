package me.divium.timetable.scrapper.model.timetable

import kotlinx.serialization.Serializable

@Serializable
data class SGroupTimetable(
    val groupName: String,
    val weeks: List<SWeek>
)