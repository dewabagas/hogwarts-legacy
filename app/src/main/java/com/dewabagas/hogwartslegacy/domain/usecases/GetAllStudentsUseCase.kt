package com.dewabagas.hogwartslegacy.domain.usecases

import com.dewabagas.hogwartslegacy.data.core.network.DataState
import com.dewabagas.hogwartslegacy.domain.entities.Student
import com.dewabagas.hogwartslegacy.domain.repositories.StudentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllStudentsUseCase @Inject constructor(
    private val repository: StudentRepository
) {
    operator fun invoke(): Flow<DataState<List<Student>>> {
        return repository.getAllStudents()
    }
}