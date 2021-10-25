package com.v2205.model

import com.v2205.model.api.AlbumWebService
import com.v2205.model.response.StudentResponse
import com.v2205.model.response.AlbumResponse

class AlbumRepository(private val AlbumWebService: AlbumWebService = AlbumWebService()) {


    suspend fun getStudents(): AlbumResponse {
        return AlbumWebService.getStudents()
    }

    suspend fun getStudent(id: String): StudentResponse {
        return AlbumWebService.getStudent(id)
    }

    suspend fun postStudent(studentResponse: StudentResponse) {
        AlbumWebService.postStudent(studentResponse)
    }
    suspend fun delStudent(id: String): StudentResponse {
        return AlbumWebService.delStudent(id)
    }

    companion object {
        @Volatile
        private var instance: AlbumRepository? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: AlbumRepository().also { instance = it }
        }
    }

}





















