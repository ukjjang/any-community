plugins {
    id("com.jinuk.toy.domain")
}

dependencies {
    implementation(project(":common:util:jbcrypt"))
    implementation(project(":common:util:jwt"))
}
