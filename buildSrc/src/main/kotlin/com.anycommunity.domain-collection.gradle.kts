plugins {
    id("com.anycommunity.kotlin")
}

dependencies {
    implementation(project(":core:domain:user"))
    testImplementation(testFixtures(project(":core:domain:user")))

    implementation(project(":core:domain:post"))
    testImplementation(testFixtures(project(":core:domain:post")))

    implementation(project(":core:domain:follow"))
    testImplementation(testFixtures(project(":core:domain:follow")))

    implementation(project(":core:domain:comment"))
    testImplementation(testFixtures(project(":core:domain:comment")))

    implementation(project(":core:domain:like"))
    testImplementation(testFixtures(project(":core:domain:like")))

    implementation(project(":core:domain:point"))
    testImplementation(testFixtures(project(":core:domain:point")))

    implementation(project(":core:domain:user_feed"))
    testImplementation(testFixtures(project(":core:domain:user_feed")))
}
