package com.dewabagas.hogwartslegacy.domain.repositories

import com.dewabagas.hogwartslegacy.data.core.network.DataState
import com.dewabagas.hogwartslegacy.domain.entities.Student
import kotlinx.coroutines.flow.Flow

interface StudentRepository {
    fun getAllStudents(): Flow<DataState<List<Student>>>
    fun getStudentDetail(id: String): Flow<DataState<Student>>
    fun getHogwartsStudents(): Flow<DataState<List<Student>>>
    fun getStudentsByHouse(house: String): Flow<DataState<List<Student>>>
}