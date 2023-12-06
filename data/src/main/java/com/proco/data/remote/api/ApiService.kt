package com.proco.data.remote.api

import com.proco.domain.model.schedule.ScheduleDto
import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Job
import com.proco.domain.model.user.User
import com.proco.domain.usecase.auth.RegisterParam
import kotlinx.collections.immutable.ImmutableList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST
    @FormUrlEncoded
    suspend fun login(@Field("email") email: String, @Field("password") password: String): Response<User>

    @POST
    suspend fun saveSchedule(@Body scheduleDto: List<ScheduleDto>): Response<Boolean>

    @POST
    @FormUrlEncoded
    suspend fun getUsers(
        @Field("search") search: String? = null,
        @Field("job_title") jobTitle: String? = null,
        @Field("country") country: String? = null,
        @Field("page") page: Int? = null,
    ): Response<List<User>>

    @POST
    @FormUrlEncoded
    suspend fun getUser(
        @Field("id") id: Int,
    ): Response<User>

    @POST
    suspend fun register(@Body registerParam: RegisterParam): Response<User>

    @GET
    suspend fun getCountries(): Response<ImmutableList<Country>>

    @GET
    suspend fun getJobs(): Response<ImmutableList<Job>>
}