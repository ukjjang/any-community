plugins {
    id("com.jinuk.toy.kotlin")
}

dependencies {
    implementation(project(":common:util:domain-helper"))

    implementation(project(":domain:user"))
    testImplementation(testFixtures(project(":domain:user")))

    implementation(project(":domain:post"))
    testImplementation(testFixtures(project(":domain:post")))

    implementation(project(":domain:follow"))
    testImplementation(testFixtures(project(":domain:follow")))

    implementation(project(":domain:comment"))
    testImplementation(testFixtures(project(":domain:comment")))

    implementation(project(":domain:like"))
    testImplementation(testFixtures(project(":domain:like")))

    implementation(project(":domain:point"))
    testImplementation(testFixtures(project(":domain:point")))
}
