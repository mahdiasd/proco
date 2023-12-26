package com.proco.data.model

import com.proco.domain.model.user.Skill
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val sex: String,
    val type: String,
    val avatar: String?,
    val companyName: String?,
    val education: String?,
    val experience: Int,
    val bio: String?,
    val cost: Int?,
    val country: Int?,
    val job: String?,
    val skills: Skill?,
)

/*
*  "id": 1,
      "firstName": "Reza",
      "lastName": "Ghavipor",
      "email": "reza@gmail.com",
      "sex": "MALE",
      "type": "MENTOR",
      "avatar": null,
      "companyName": "tst",
      "education": "MASTERS",
      "experience": 1,
      "bio": "bio",
      "cost": "null",
      "country": "Afghanistan",
      "job": "Mobile App Developer",
      "skills": [
        {
          "id": 1,
          "name": "string"
        }
      ],
      "expertises": [
        {
          "id": 2,
          "name": "FrontEnd Development"
        },
        {
          "id": 4,
          "name": "Infrastructure as Code (IaC)"
        }
      ]
*
* */