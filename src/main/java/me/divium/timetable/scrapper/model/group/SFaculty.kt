package me.divium.timetable.scrapper.model.group

import kotlinx.serialization.Serializable

@Serializable
data class SFaculty(val name: String, val years: List<SYear>)