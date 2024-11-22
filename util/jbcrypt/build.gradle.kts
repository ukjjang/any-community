plugins {
    id("com.anycommunity.lib")
}

dependencies {
    implementation(project(":definition"))

    implementation(Dependencies.JBcrypt.JBCRYPT)
}
