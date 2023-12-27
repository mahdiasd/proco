package com.proco.data.remote.api

import com.proco.data.model.LoginResponse
import com.proco.data.model.RegisterRequest
import com.proco.data.model.RegisterResponse
import com.proco.data.model.UserCacheResponse
import com.proco.data.model.UserResponse
import com.proco.data.model.UserSummaryResponse
import com.proco.domain.model.network.NetworkResponse
import com.proco.domain.model.schedule.ScheduleDto
import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Job
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("/auth/login")
    @FormUrlEncoded
    suspend fun login(@Field("email") email: String, @Field("password") password: String): NetworkResponse<LoginResponse>

    @POST("user-schedule")
    suspend fun saveSchedule(@Body scheduleDto: List<ScheduleDto>): NetworkResponse<List<ScheduleDto>>

    @GET("/user/mentors")
    suspend fun getUsers(
        @Query("name") name: String? = null,
        @Query("job") jobTitle: String? = null,
        @Query("country") country: String? = null,
        @Query("page") page: Int? = null,
    ): NetworkResponse<List<UserSummaryResponse>>

    @GET("/user-schedule/mentor/{id}")
    suspend fun getSchedule(@Path("id") id: Int, ): NetworkResponse<List<ScheduleDto>>

    @GET("user/mentor/{id}")
    suspend fun getUser(@Path("id") id: Int, ): NetworkResponse<UserResponse>

    @POST("/auth/register")
    suspend fun register(@Body registerParam: RegisterRequest): NetworkResponse<RegisterResponse>

    @PATCH("/user/mentor")
    @FormUrlEncoded
    suspend fun updatePrice(@Field("cost") cost: Int): NetworkResponse<UserCacheResponse>

    @GET("/country")
    suspend fun getCountries(): NetworkResponse<List<Country>>

    @GET("/job")
    suspend fun getJobs(): NetworkResponse<List<Job>>
}