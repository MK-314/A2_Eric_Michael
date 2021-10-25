package com.v2205.model.response

import com.google.gson.annotations.SerializedName


data class AlbumResponse(val data: List<StudentResponse>)

data class StudentResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val imageUrl: String,
    @SerializedName("techName") val techName: String
)




















