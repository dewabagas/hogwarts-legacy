package com.dewabagas.hogwartslegacy.presentation.students

import android.util.Log
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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StudentListViewModel @Inject constructor(
    private val getAllStudentsUseCase: GetAllStudentsUseCase
) : ViewModel() {

    private val _students = MutableStateFlow<DataState<List<Student>>>(DataState.Loading)
    val students: StateFlow<DataState<List<Student>>> = _students

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

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
                    Timber.tag("GetAllStudentsUseCase").e("dataState: %s", dataState)
                    _students.value = dataState
                }
        }
    }

    fun refreshStudents() {
        viewModelScope.launch {
            _isRefreshing.value = true
            getAllStudentsUseCase()
                .catch { exception ->
                    _students.value = DataState.Error(exception as Exception)
                }
                .collect { dataState ->
                    _students.value = dataState
                    _isRefreshing.value = false
                }
        }
    }

    fun searchStudents(query: String) {
        viewModelScope.launch {
            _students.value = DataState.Loading
            getAllStudentsUseCase()
                .catch { exception ->
                    _students.value = DataState.Error(exception as Exception)
                }
                .collect { dataState ->
                    val filteredStudents = (dataState as? DataState.Success)?.data?.filter {
                        it.name!!.contains(query, ignoreCase = true) // Filter berdasarkan nama siswa
                    }
                    _students.value = if (filteredStudents != null) DataState.Success(filteredStudents) else DataState.Success(emptyList())
                }
        }
    }
}