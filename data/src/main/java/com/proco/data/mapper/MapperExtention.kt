package com.proco.data.mapper

import com.proco.data.model.UserCacheResponse
import com.proco.data.model.UserResponse
import com.proco.data.model.UserSummaryResponse
import com.proco.domain.model.schedule.HourRange
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.schedule.ScheduleDto
import com.proco.domain.model.user.Gender
import com.proco.domain.model.user.User
import com.proco.domain.model.user.UserCache
import com.proco.domain.model.user.UserSummary
import com.proco.domain.model.user.UserType
import com.proco.domain.model.user.apiName
import com.proco.extention.findIndex
import com.proco.extention.safeAdd
import com.proco.extention.toInstant
import com.proco.extention.toLocalDate
import kotlinx.collections.immutable.toImmutableList

fun List<ScheduleDto>.toSchedule(): List<Schedule> {
    val scheduleList = mutableListOf<Schedule>()

    this.forEach { scheduleDto ->
        val date = scheduleDto.start.toInstant()
        val hourRange = HourRange(start = scheduleDto.start.toInstant(), scheduleDto.end.toInstant())

        scheduleList.findIndex { it.date.compareTo(date.toLocalDate()) == 0 }?.let { index ->
            scheduleList[index] = scheduleList[index].copy(hours = scheduleList[index].hours.toMutableList().apply { safeAdd(hourRange) }.toImmutableList())
        } ?: run {
            scheduleList.add(Schedule(date = date.toLocalDate(), hours = listOf(hourRange).toImmutableList()))
        }
    }
    return scheduleList
}


fun UserSummaryResponse.toUserSummary(): UserSummary {
    return UserSummary(
        id = id,
        name = firstName,
        family = lastName,
        avatar = avatar ?: "",
        gender = when (sex) {
            Gender.Male.apiName() -> Gender.Male
            Gender.Female.apiName() -> Gender.Female
            Gender.NonBinary.apiName() -> Gender.NonBinary
            else -> {
                Gender.Unknown
            }
        },
        email = email,
        type = when (type) {
            UserType.Mentee.apiName() -> UserType.Mentee
            UserType.Mentor.apiName() -> UserType.Mentor
            else -> {
                throw Exception(
                    "We were unable to determine your user type." +
                            "\nPlease try re-logging into your account." +
                            "\nIf the issue persists, please clear the app's data or contact our customer support for further assistance."
                )
            }
        },
        cost = cost ?: 0,
        job = job ?: "",
        country = country,
        expertises = expertises.toImmutableList(),
        skills = skills?.toImmutableList()
    )
}

fun UserCacheResponse.toUserCache(): UserCache {
    return UserCache(
        id = id,
        name = firstName,
        family = lastName,
        email = email,
        type = when (type) {
            UserType.Mentee.apiName() -> UserType.Mentee
            UserType.Mentor.apiName() -> UserType.Mentor
            else -> {
                throw Exception(
                    "We were unable to determine your user type." +
                            "\nPlease try re-logging into your account." +
                            "\nIf the issue persists, please clear the app's data or contact our customer support for further assistance."
                )
            }
        },
        cost = if (cost.isNullOrEmpty() || cost.contains("null", true)) 0 else cost.toInt(),
    )
}

fun List<UserSummaryResponse>.toUserSummaryList(): List<UserSummary> {
    val temp: MutableList<UserSummary> = mutableListOf()
    this.forEach {
        temp.safeAdd(it.toUserSummary())
    }
    return temp
}

fun UserResponse.toUser(): User {
    return User(
        id = id,
        name = firstName,
        family = lastName,
        avatar = avatar ?: "",
        gender = when (sex) {
            Gender.Male.apiName() -> Gender.Male
            Gender.Female.apiName() -> Gender.Female
            Gender.NonBinary.apiName() -> Gender.NonBinary
            else -> {
                Gender.Unknown
            }
        },
        email = email,
        type = when (type) {
            UserType.Mentee.apiName() -> UserType.Mentee
            UserType.Mentor.apiName() -> UserType.Mentor
            else -> {
                throw Exception(
                    "We were unable to determine your user type." +
                            "\nPlease try re-logging into your account." +
                            "\nIf the issue persists, please clear the app's data or contact our customer support for further assistance."
                )
            }
        },
        cost = cost ?: 0,
        job = job ?: "",
        country = country ?: "",
        expertises = expertises?.toImmutableList(),
        bio = bio,
        company = companyName ?: "",
        education = education ?: "",
        experience = experience,
        skills = skills?.toImmutableList()
    )
}
