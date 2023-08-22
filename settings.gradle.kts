pluginManagement {
    repositories {
        mavenCentral()
    }
}
rootProject.name = "BaseProject"
include(":app")
// include(":data")
// include(":commons")
// include(":libraries")
// include(":domain")
// include(":features:splash")
// include(":features:home")
// include(":features:search")
// include(":features:feedback")
// include(":features:call")
// include(":features:settings")
// include(":features:chat")
// include(":features:container")
include(":core:views")
include(":core:utils")
// include(":features:friends")
// include(":features:message")
// include(":features:ringing")

include(":data")
include(":commons")
include(":domain")
