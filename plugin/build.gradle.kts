plugins {
    `java-gradle-plugin`
    id("maven-publish")
    id("com.gradle.plugin-publish") version "0.14.0"
    id("org.jetbrains.kotlin.jvm") version "1.4.31"
}

repositories {
    mavenCentral()
}

pluginBundle {
    website = "https://github.com/aniaan/gradle-spring-profile"
    vcsUrl = "https://github.com/aniaan/gradle-spring-profile.git"
    tags = listOf("Spring Boot")
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

gradlePlugin {
    // Define the plugin
    val profile by plugins.creating {
        id = "com.github.aniaan.gradle-spring-profile"
        implementationClass = "com.github.aniaan.spring.profile.GradleSpringProfilePlugin"
        displayName = "Like maven's profile replacement plugin"
        description = "Like maven's profile replacement plugin"
        version = "0.0.2"
    }
}

publishing {
    repositories {
        maven {
            name = "github"
            url = uri("https://maven.pkg.github.com/OWNER/gradle-spring-profile")
            credentials(PasswordCredentials::class)
        }
    }
}

// Add a source set for the functional test suite
val functionalTestSourceSet = sourceSets.create("functionalTest") {
}


gradlePlugin.testSourceSets(functionalTestSourceSet)
configurations["functionalTestImplementation"].extendsFrom(configurations["testImplementation"])

// Add a task to run the functional tests
val functionalTest by tasks.registering(Test::class) {
    testClassesDirs = functionalTestSourceSet.output.classesDirs
    classpath = functionalTestSourceSet.runtimeClasspath
}

tasks.check {
    // Run the functional tests as part of `check`
    dependsOn(functionalTest)
}
