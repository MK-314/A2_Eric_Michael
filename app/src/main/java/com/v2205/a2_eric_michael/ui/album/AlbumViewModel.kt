package com.v2205.a2_eric_michael.ui.album

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v2205.model.AlbumRepository
import com.v2205.model.response.StudentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumViewModel(private val repository: AlbumRepository = AlbumRepository.getInstance()) :
    ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val students = getStudents()
            studentsState.value = students
        }
    }

    val studentsState: MutableState<List<StudentResponse>> = mutableStateOf(mutableListOf<StudentResponse>())

    private suspend fun getStudents(): List<StudentResponse> {
        return repository.getStudents().data
    }
}






















