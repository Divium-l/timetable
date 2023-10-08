package me.divium.timetable.scrapper.model.group

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
data class SUniversity(val name: String, val faculties: List<SFaculty>)