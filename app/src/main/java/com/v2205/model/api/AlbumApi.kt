package com.v2205.model.api

import com.v2205.model.response.StudentResponse
import com.v2205.model.response.AlbumResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


class AlbumWebService {
    private var api: studentsApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rubymichaelkashkov.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(studentsApi::class.java)
    }

    suspend fun getStudents(): AlbumResponse {
        return api.getStudents()
    }

    suspend fun getStudent(id: String): StudentResponse {
        return api.getStudent(id)
    }

    suspend fun postStudent(studentResponse: StudentResponse) {
        api.postStudent(studentResponse)
    }
    suspend fun delStudent(id: String): StudentResponse {
        return api.delStudent(id)
    }

    interface studentsApi {
        @GET("students")
        suspend fun getStudents(): AlbumResponse

        @GET("students/{id}")
        suspend fun getStudent(@Path("id") id: String): StudentResponse

        @POST("students")
        suspend fun postStudent(@Body studentResponse: StudentResponse)

        @DELETE("students/{id}")
        suspend fun delStudent(@Path("id") id: String): StudentResponse
    }
}
