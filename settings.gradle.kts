import java.net.URI

include(":presentation:splash")


include(":presentation:splash")


pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven{
            url = URI("https://jitpack.io")
        }
    }
}

rootProject.name = "Proco"
include(":app")
include(":data")
include(":domain")
include(":common:network")
include(":common:utils")
include(":presentation:login")
include(":common:ui")
include(":presentation:register")
include(":presentation:schedule")
include(":presentation:search")
include(":presentation:filter")
include(":presentation:main")
include(":presentation:profile")
