package com.dewabagas.hogwartslegacy.data.remotes

import com.dewabagas.hogwartslegacy.data.models.StudentDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("characters")
    suspend fun getAllStudents(): List<StudentDto>

    @GET("character/{id}")
    suspend fun getStudentDetail(@Path("id") id: String): StudentDto

    @GET("characters/students")
    suspend fun getHogwartsStudents(): List<StudentDto>

    @GET("characters/house/{house}")
    suspend fun getStudentsByHouse(@Path("house") house: String): List<StudentDto>
}