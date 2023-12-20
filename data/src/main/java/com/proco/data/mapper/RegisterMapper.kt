package com.proco.data.mapper

import com.proco.data.model.RegisterRequest
import com.proco.domain.usecase.auth.RegisterParam

class RegisterMapper : Mapper<RegisterParam, RegisterRequest> {
    override fun mapFrom(from: RegisterParam): RegisterRequest {
        return RegisterRequest(
            name = from.name,
            family = from.family,
            email = from.email,
            password = from.password,
            gender = from.gender.toString().uppercase(),
            job = from.job?.id,
            expertise = from.expertises?.map { it.id },
            experience = from.experience?.index,
            education = from.education.toString().uppercase(),
            company = from.company,
            skills = from.skills?.map { it.name },
            bio = from.bio,
            country = from.country?.id,
            userType = from.userType.toString().uppercase()
        )
    }
}