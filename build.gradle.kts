// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.sonarqube") version "3.2.0"

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

//sonarqube {
//    properties {
//        property("sonar.projectKey", "your-project-key")
//        property("sonar.host.url", "http://localhost:9000")
//        property("sonar.login", "your-authentication-token")
//    }
//}
