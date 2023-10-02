package me.divium.timetable.scrapper.model.group

import kotlinx.serialization.Serializable

@Serializable
data class SYear (val number: Byte, val SGroups: List<SGroup>)