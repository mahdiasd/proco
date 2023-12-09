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
            gender = from.gender.toString(),
            job =  from.job?.id,
            expertise = from.expertise?.id,
            experience = from.experience?.index,
            company = from.company,
            skills = from.skills?.map { it.name },
            bio = from.bio,
            country = from.country?.id,
            userType = from.userType.toString()
        )
    }
}