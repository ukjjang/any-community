plugins {
    id("com.anycommunity.domain")
}

dependencies {
    implementation(project(":util:jbcrypt"))
    implementation(project(":util:jwt"))
}
