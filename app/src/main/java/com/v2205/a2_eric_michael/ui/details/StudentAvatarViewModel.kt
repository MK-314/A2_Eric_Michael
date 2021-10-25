package com.v2205.a2_eric_michael.ui.details

import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v2205.model.AlbumRepository
import com.v2205.model.response.StudentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentAvatarViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val repository: AlbumRepository = AlbumRepository.getInstance()
    val studentId = savedStateHandle.get<String>("student_id") ?: ""
    var studentState: MutableState<StudentResponse> = mutableStateOf(StudentResponse("1", "", "", "",""))


    init {
        viewModelScope.launch(Dispatchers.IO) {
            studentState.value = getStudent(studentId)
        }
    }
    suspend fun deleteStudent(id: String) {
        val job = viewModelScope.launch(Dispatchers.IO) {
            delStudent(id)
        }
        job.join()
    }

    private suspend fun getStudent(id: String): StudentResponse {
        return repository.getStudent(id)
    }
    private suspend fun delStudent(id: String): StudentResponse {
        return repository.delStudent(id)
    }
}























