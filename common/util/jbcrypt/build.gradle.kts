plugins {
    id("com.jinuk.toy.lib")
}

dependencies {
    implementation(project(":common:value"))

    implementation(Dependencies.JBcrypt.JBCRYPT)
}
