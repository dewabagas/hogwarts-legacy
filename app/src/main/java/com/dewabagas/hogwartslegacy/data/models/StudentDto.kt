package com.dewabagas.hogwartslegacy.data.models

import com.dewabagas.hogwartslegacy.domain.entities.Student
import com.dewabagas.hogwartslegacy.domain.entities.Wand


data class StudentDto(
    val id: String?,
    val name: String?,
    val species: String?,
    val gender: String?,
    val house: String?,
    val dateOfBirth: String?,
    val yearOfBirth: Int?,
    val wizard: Boolean?,
    val ancestry: String?,
    val eyeColour: String?,
    val hairColour: String?,
    val wand: WandDto?,
    val patronus: String?,
    val hogwartsStudent: Boolean?,
    val hogwartsStaff: Boolean?,
    val actor: String?,
    val alive: Boolean?,
    val image: String?
) {
    fun toStudent(): Student {
        return Student(
            id = id,
            name = name,
            species = species,
            gender = gender,
            house = house,
            dateOfBirth = dateOfBirth,
            yearOfBirth = yearOfBirth,
            wizard = wizard,
            ancestry = ancestry,
            eyeColour = eyeColour,
            hairColour = hairColour,
            wand = wand?.toWand(),
            patronus = patronus,
            hogwartsStudent = hogwartsStudent,
            hogwartsStaff = hogwartsStaff,
            actor = actor,
            alive = alive,
            image = image
        )
    }
}

data class WandDto(
    val wood: String?,
    val core: String?,
    val length: Double?
) {
    fun toWand(): Wand {
        return Wand(
            wood = wood,
            core = core,
            length = length
        )
    }
}
