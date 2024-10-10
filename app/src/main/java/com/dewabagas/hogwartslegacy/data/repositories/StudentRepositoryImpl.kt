package com.dewabagas.hogwartslegacy.data.repositories

import com.dewabagas.hogwartslegacy.data.core.network.DataState
import com.dewabagas.hogwartslegacy.data.remotes.ApiService
import com.dewabagas.hogwartslegacy.domain.entities.Student
import com.dewabagas.hogwartslegacy.domain.repositories.StudentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : StudentRepository {
    override fun getAllStudents(): Flow<DataState<List<Student>>> = flow {
        try {
            emit(DataState.Loading) // Emit state loading
            val students = apiService.getAllStudents().map { it.toStudent() }
            emit(DataState.Success(students)) // Emit state success dengan data
        } catch (e: Exception) {
            emit(DataState.Error(e)) // Emit state error jika ada exception
        }
    }

    override fun getStudentDetail(id: String): Flow<DataState<Student>> = flow {
        try {
            emit(DataState.Loading) // Emit state loading
            val student = apiService.getStudentDetail(id).toStudent()
            emit(DataState.Success(student)) // Emit state success dengan data
        } catch (e: Exception) {
            emit(DataState.Error(e)) // Emit state error jika ada exception
        }
    }

    override fun getHogwartsStudents(): Flow<DataState<List<Student>>> = flow {
        try {
            emit(DataState.Loading) // Emit state loading
            val students = apiService.getHogwartsStudents().map { it.toStudent() }
            emit(DataState.Success(students)) // Emit state success dengan data
        } catch (e: Exception) {
            emit(DataState.Error(e)) // Emit state error jika ada exception
        }
    }

    override fun getStudentsByHouse(house: String): Flow<DataState<List<Student>>> = flow {
        try {
            emit(DataState.Loading) // Emit state loading
            val students = apiService.getStudentsByHouse(house).map { it.toStudent() }
            emit(DataState.Success(students)) // Emit state success dengan data
        } catch (e: Exception) {
            emit(DataState.Error(e)) // Emit state error jika ada exception
        }
    }
}