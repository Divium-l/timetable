package me.divium.timetable.scrapper.model

data class Timetable(
    val groupName: String,
    val weeks: List<Week>
)