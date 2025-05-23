// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.sonarqube") version "6.0.1.5171"

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

sonar {
    properties {
        property("sonar.projectKey", "JetNews")
        property("sonar.host.url", "http://202.79.29.108:9000")
        property("sonar.projectName", "JetNews")
    }
}