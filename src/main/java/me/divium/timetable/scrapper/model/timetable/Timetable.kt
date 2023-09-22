package me.divium.timetable.scrapper.model.timetable

data class Timetable(
    val groupName: String,
    val weeks: List<Week>
)