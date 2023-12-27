package com.proco.domain.fake_data

import com.proco.domain.model.filter.UserFilter
import com.proco.domain.model.schedule.HourRange
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Expertise
import com.proco.domain.model.user.Gender
import com.proco.domain.model.user.Job
import com.proco.domain.model.user.Skill
import com.proco.domain.model.user.User
import com.proco.domain.model.user.UserSummary
import com.proco.domain.model.user.UserType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.Instant
import java.time.LocalDate


object FakeData {
    fun users(): MutableList<User> {
        return listOf(
            user().copy(name = "Mahdi", family = "Asadollahpour"),
            user().copy(name = "Reza", family = "Ghavipour"),
            user().copy(name = "Amir", family = "Hammamian"),
            user().copy(name = "Hamed", family = "Daemi"),
        ).toMutableList()
    }

    fun usersSummary(): MutableList<UserSummary> {
        return listOf(
            UserSummary(
                id = 7827,
                name = "Rashaunda",
                avatar = "Lacy",
                family = "Nacole",
                gender = Gender.Male,
                email = "Raheem",
                type = UserType.Mentee,
                cost = 5457,
                job = "",
                country = null,
                expertises = expertises(),
                skills = null
            )
        ).toMutableList()
    }

    fun user(): User {
        return User(
            id = 6448,
            name = "Carlin",
            family = "Tyanna",
            email = "Anabel",
            type = UserType.Mentee,
            avatar = "Lexi",
            gender = Gender.Male,
            job = "Temika",
            experience = 4375,
            company = "Charline",
            education = "Latoria",
            skills = null,
            expertises = null,
            bio = null,
            cost = 351,
            country = "Arielle"
        )
    }

    fun filter(): UserFilter {
        return UserFilter()
    }

    fun skills(): MutableList<Skill> {
        return listOf(
            Skill(name = "java"),
            Skill(name = "kotlin"),
            Skill(name = "nodejs"),
            Skill(name = "laravel"),
            Skill(name = "php"),
            Skill(name = "nestjs"),
            Skill(name = "jetpack-compose"),
            Skill(name = "angular"),
            Skill(name = "python"),
            Skill(name = "C++"),
            Skill(name = "JavaScript"),
            Skill(name = "Swift"),
            Skill(name = "Ruby"),
        ).toMutableList()
    }

    fun schedules(): List<Schedule> {
        return listOf(
            Schedule(LocalDate.now().minusMonths(1), hours = hourRange().toImmutableList().take(4).toImmutableList()),
            Schedule(LocalDate.now(), hours = hourRange().toImmutableList()),
            Schedule(LocalDate.now().plusDays(1), hours = hourRange().toImmutableList().take(2).toImmutableList())
        )
    }

    fun hourRange(): List<HourRange> {
        return listOf(
            HourRange(Instant.now(), Instant.now().plusSeconds(1000)),
            HourRange(Instant.now().plusSeconds(1000), Instant.now().plusSeconds(2000)),
            HourRange(Instant.now().plusSeconds(3000), Instant.now().plusSeconds(4000)),
            HourRange(Instant.now().plusSeconds(5000), Instant.now().plusSeconds(6000)),
            HourRange(Instant.now().plusSeconds(7000), Instant.now().plusSeconds(8000)),
            HourRange(Instant.now().plusSeconds(9000), Instant.now().plusSeconds(10000)),
            HourRange(Instant.now().plusSeconds(11000), Instant.now().plusSeconds(120000)),
        )
    }

    fun countries(): ImmutableList<Country> {
        var index = 0
        return listOf(
            Country(index++, name = "Iran", code = "IR"),
            Country(index++, name = "Brazil", code = "IR"),
            Country(index++, name = "Spain", code = "IR"),
            Country(index++, name = "England", code = "IR"),
            Country(index++, name = "Morocco", code = "IR"),
            Country(index++, name = "Afghanistan", code = "IR"),
            Country(index++, name = "Albania", code = "IR"),
            Country(index++, name = "Algeria", code = "IR"),
            Country(index++, name = "Andorra", code = "IR"),
            Country(index++, name = "Angola", code = "IR"),
            Country(index++, name = "Antigua and Barbuda", code = "IR"),
            Country(index++, name = "Argentina", code = "IR"),
            Country(index++, name = "Armenia", code = "IR"),
            Country(index++, name = "Australia", code = "IR"),
            Country(index++, name = "Austria", code = "IR"),
            Country(index++, name = "Azerbaijan", code = "IR"),
            Country(index++, name = "Bahamas", code = "IR"),
            Country(index++, name = "Bahrain", code = "IR"),
            Country(index++, name = "Bangladesh", code = "IR"),
            Country(index++, name = "Barbados", code = "IR"),
            Country(index++, name = "Belarus", code = "IR"),
            Country(index++, name = "Belgium", code = "IR"),
            Country(index, name = "Belize", code = "IR"),
        ).toImmutableList()
    }

    fun jobTitles(): ImmutableList<Job> {
        return listOf(
            Job(name = "Android Developer", expertises = expertises()),
            Job(name = "Backend Developer", expertises = expertises()),
            Job(name = "Frontend Developer", expertises = expertises())
        ).toImmutableList()
    }

    fun expertises(): ImmutableList<Expertise> {
        return listOf(
            Expertise(name = "Kotlin"),
            Expertise(name = "Java"),
            Expertise(name = "Javascript"),
            Expertise(name = "Laravel"),
            Expertise(name = "Nodejs"),
        ).toImmutableList()
    }
}
