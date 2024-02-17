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
    }
}


rootProject.name = "MoviesApp"
include(":app")

include(":core:db")
include(":core:network")
include(":core:designsystem")
include(":core:widget")
include(":feature:popular:api")
include(":feature:popular:impl")
include(":feature:favorite:api")
include(":feature:favorite:impl")
include(":feature:details:api")
include(":feature:details:impl")
