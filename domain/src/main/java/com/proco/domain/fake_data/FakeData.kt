package com.proco.domain.fake_data

import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.time.HoursOfDay
import com.proco.domain.model.user.Country
import com.proco.domain.model.user.Expertise
import com.proco.domain.model.user.Job
import com.proco.domain.model.user.Skill
import com.proco.domain.model.user.User
import com.proco.domain.model.user.UserType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


object FakeData {
    fun users(): MutableList<User> {
        return listOf(
            user().copy(name = "Mahdi", family = "Asadollahpour"),
            user().copy(name = "Reza", family = "Ghavipour"),
            user().copy(name = "Amir", family = "Hammamian"),
            user().copy(name = "Hamed", family = "Daemi"),
        ).toMutableList()
    }

    fun user(): User {
        return User(
            id = 0,
            name = "Mahdi",
            family = "Asadollahpour",
            type = UserType.Mentee,
            avatar = "",
            job = Job(name = "", expertises = expertises()),
            expertise = Expertise(0, name = "Android Developer"),
            experience = 0,
            company = "",
            skills = listOf(Skill(name = "#android")).toImmutableList(),
            bio = "Experienced Android developer with over 5 years building and deploying apps to the Google Play Store. Skilled in Java, Kotlin, XML, Firebase, REST APIs, SQLite databases, and Material Design. Passionate about turning ideas into performant, beautiful mobile apps that delight users. Built messaging, weather, sports, and location-based apps each with over 100k downloads. Excited to join a fast-paced team to continue creating meaningful impacts in people's lives through mobile technology. Constantly learning and staying on top of latest Android advancements.",
            country = Country(code = "IR", name = "Iran"),
            price = 1
        )

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
        val temp = LocalDateTime.of(LocalDate.parse("2023-12-09"), LocalTime.parse("2023-12-09"))
        return listOf(
        )
    }

    fun hoursOfDay(): List<HoursOfDay> {
        return listOf(
            HoursOfDay("00:00 - 01:00", 0),
            HoursOfDay("01:00 - 02:00", 1),
            HoursOfDay("02:00 - 03:00", 2),
            HoursOfDay("03:00 - 04:00", 3),
            HoursOfDay("04:00 - 05:00", 4),
            HoursOfDay("05:00 - 06:00", 5),
            HoursOfDay("06:00 - 07:00", 6),
            HoursOfDay("07:00 - 08:00", 7),
            HoursOfDay("08:00 - 09:00", 8),
            HoursOfDay("09:00 - 10:00", 9),
            HoursOfDay("10:00 - 11:00", 10),
            HoursOfDay("11:00 - 12:00", 11),
            HoursOfDay("12:00 - 13:00", 12),
            HoursOfDay("13:00 - 14:00", 13),
            HoursOfDay("14:00 - 15:00", 14),
            HoursOfDay("15:00 - 16:00", 15),
            HoursOfDay("16:00 - 17:00", 16),
            HoursOfDay("17:00 - 18:00", 17),
            HoursOfDay("18:00 - 19:00", 18),
            HoursOfDay("19:00 - 20:00", 19),
            HoursOfDay("20:00 - 21:00", 20),
            HoursOfDay("21:00 - 22:00", 21),
            HoursOfDay("22:00 - 23:00", 22),
            HoursOfDay("23:00 - 24:00", 23)
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
