package me.divium.timetable.scrapper.model.timetable

data class SGroupTimetable(
    val groupName: String,
    val sWeeks: List<SWeek>
)