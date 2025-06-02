// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.sonarqube") version "6.0.1.5171"

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

//    id("com.google.dagger.hilt.android") version "2.56.2" apply false
    id("com.google.dagger.hilt.android") version "2.56.2" apply false
//    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    id("com.google.devtools.ksp") version libs.versions.ksp.get() apply false
    alias(libs.plugins.kotlin.compose) apply false

    val room_version = "2.7.1"
    id("androidx.room") version "$room_version" apply false
}

sonar {
    properties {
        property("sonar.projectKey", "JetNews")
        property("sonar.host.url", "https://dev-sonarqube.wingecosys.com")
        property("sonar.projectName", "JetNews")
        property("sonar.findbugs.allowuncompiledcode", true)
    }
}

//buildscript {
//    dependencies {
//        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
//    }
//}