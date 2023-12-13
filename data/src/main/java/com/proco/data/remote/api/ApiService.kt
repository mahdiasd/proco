package com.proco.data.remote.api

import com.proco.data.model.LoginResponse
import com.proco.data.model.RegisterRequest
import com.proco.domain.model.network.NetworkResponse
import com.proco.domain.model.schedule.ScheduleDto
import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Job
import com.proco.domain.model.user.User
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/auth/login")
    @FormUrlEncoded
    suspend fun login(@Field("email") email: String, @Field("password") password: String): NetworkResponse<LoginResponse>

    @POST
    suspend fun saveSchedule(@Body scheduleDto: List<ScheduleDto>): NetworkResponse<Boolean>

    @POST
    @FormUrlEncoded
    suspend fun getUsers(
        @Field("search") search: String? = null,
        @Field("job_title") jobTitle: String? = null,
        @Field("country") country: String? = null,
        @Field("page") page: Int? = null,
    ): NetworkResponse<List<User>>

    @POST
    @FormUrlEncoded
    suspend fun getUser(
        @Field("id") id: Int,
    ): NetworkResponse<User>

    @POST("/auth/register")
    suspend fun register(@Body registerParam: RegisterRequest): NetworkResponse<User>

    @GET("/country")
    suspend fun getCountries(): NetworkResponse<List<Country>>

    @GET("/job")
    suspend fun getJobs(): NetworkResponse<List<Job>>
}