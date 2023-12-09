package com.proco.data.model

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class RegisterRequest(
    val name: String = "",
    val family: String = "",
    val email: String = "",
    val password: String = "",
    val gender: String,
    val job: Int?,
    val expertise: Int?,
    val experience: Int? = null,
    val company: String? = null,
    val skills: List<String>?,
    val bio: String = "",
    val country: Int?,
    val userType: String
)

//{
//    "firstName": "<string>",
//    "lastName": "<string>",
//    "password": "<string>",
//    "email": "<string>",
//    "company": "<string>",
//    "country_id": "<number>",
//    "experience": "<string>",
//    "skills": {},
//    "bio": "<string>",
//    "education": "<string>",
//    "Job": "<number>",
//    "sex": "<string>",
//    "expertise": {},
//    "type": "<string>"
//}