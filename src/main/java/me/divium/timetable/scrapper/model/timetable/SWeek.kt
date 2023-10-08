package me.divium.timetable.scrapper.model.timetable

import kotlinx.serialization.Serializable

@Serializable
data class SWeek (
    val name: String,
    val days: List<SDay>
)