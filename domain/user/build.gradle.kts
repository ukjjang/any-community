plugins {
    id("com.jinuk.toy.domain")
}

dependencies {
    implementation(project(":util:jbcrypt"))
    implementation(project(":util:jwt"))
}
