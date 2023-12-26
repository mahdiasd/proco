package com.proco.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.proco.domain.encrypt_shared.TokenDataStore
import com.proco.extention.dLog
import com.proco.utils.MyConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val contentType = "application/json".toMediaType()

    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    @Singleton
    @Provides
    fun getToken(tokenDataStore: TokenDataStore): String {
        tokenDataStore.readToken().dLog("Token: ")
        return tokenDataStore.readToken()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenDataStore: TokenDataStore): OkHttpClient.Builder {
        val http = OkHttpClient.Builder()
            .connectTimeout(80, TimeUnit.SECONDS)
            .readTimeout(80, TimeUnit.SECONDS)
            .writeTimeout(80, TimeUnit.SECONDS)

        http.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + getToken(tokenDataStore))
                .header("app-type", "panel")
                .header("Accept", "application/json")
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }).build()

        // TODO remove logs from release mode
        val interceptor = HttpLoggingInterceptor(logger = {
            it.dLog(tag = "Retrofit")
        })
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        http.addInterceptor(interceptor).build()

        return http
    }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient.Builder, json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MyConstant.BaseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client.build())
            .build()
    }


}