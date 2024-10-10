package com.dewabagas.hogwartslegacy.presentation.students

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dewabagas.hogwartslegacy.data.core.network.DataState
import com.dewabagas.hogwartslegacy.domain.entities.Student
import com.dewabagas.hogwartslegacy.domain.usecases.GetAllStudentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentListViewModel @Inject constructor(
    private val getAllStudentsUseCase: GetAllStudentsUseCase
) : ViewModel() {

    private val _students = MutableStateFlow<DataState<List<Student>>>(DataState.Loading)
    val students: StateFlow<DataState<List<Student>>> = _students

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getAllStudents()
    }

    fun getAllStudents() {
        viewModelScope.launch {
            getAllStudentsUseCase()
                .catch { exception ->
                    _students.value = DataState.Error(exception as Exception)
                }
                .collect { dataState ->
                    _students.value = dataState
                }
        }
    }
}