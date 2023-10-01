package me.divium.timetable.scrapper.model.group

import kotlinx.serialization.Serializable

@Serializable
data class SDepartment(val name: String, val years: List<SYear>)