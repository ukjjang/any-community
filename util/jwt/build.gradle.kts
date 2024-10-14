plugins {
    id("com.jinuk.toy.util")
}

dependencies {
    implementation(Dependencies.JJWT.API)
    implementation(Dependencies.JJWT.IMPL)
    implementation(Dependencies.JJWT.JACKSON)
    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_SECURITY)
}
