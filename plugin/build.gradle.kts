plugins {
    `java-gradle-plugin`
    id("maven-publish")
    id("com.gradle.plugin-publish") version "0.14.0"
    id("org.jetbrains.kotlin.jvm") version "1.4.31"
}

group = "com.github.aniaan"
version = "1.1.0-RELEASE"

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
    plugins {
        create("profilePlugin") {
            id = "com.github.aniaan.gradle-spring-profile"
            implementationClass = "com.github.aniaan.spring.profile.GradleSpringProfilePlugin"
            displayName = "Like maven's profile replacement plugin"
            description = "Like maven's profile replacement plugin"
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
