plugins {
    id("com.jinuk.toy.lib")
}

dependencies {
    implementation(Dependencies.JBcrypt.JBCRYPT)

    api(project(":common:value"))
}
