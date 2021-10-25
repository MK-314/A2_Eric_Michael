package com.v2205.a2_eric_michael.ui.post

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v2205.model.AlbumRepository
import com.v2205.model.response.StudentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel() : ViewModel() {
    private val repository: AlbumRepository = AlbumRepository.getInstance()
    var studentState: MutableState<StudentResponse> =
        mutableStateOf(StudentResponse("", "", "", "",""))

    suspend fun simplePost() {
        val job = viewModelScope.launch(Dispatchers.IO) {
            postStudent(studentState.value)
        }
        job.join()
    }

    private suspend fun postStudent(studentResponse: StudentResponse) {
        repository.postStudent(studentResponse)
    }
}