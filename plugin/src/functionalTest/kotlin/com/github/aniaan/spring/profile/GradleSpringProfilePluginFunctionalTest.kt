package com.github.aniaan.spring.profile


import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class GradleSpringProfilePluginFunctionalTest {

    @get:Rule
    var testProjectDir = TemporaryFolder()

    private lateinit var buildFile: File
    private lateinit var settingFile: File
    private lateinit var runner: GradleRunner

    @BeforeTest
    fun setup() {
        buildFile = testProjectDir.newFile("build.gradle.kts")
        settingFile = testProjectDir.newFile("settings.gradle.kts")

        testProjectDir.newFolder("src", "main", "resources")
        testProjectDir.newFolder("src", "test", "resources")

        val template = "\${spring.active}"

        testProjectDir.newFile("src/test/resources/application.yaml").writeText(
            """
            spring:
              profiles:
                active: $template
        """.trimIndent()
        )
        testProjectDir.newFile("src/test/resources/application.yml").writeText(
            """
            spring:
              profiles:
                active: $template
        """.trimIndent()
        )
        testProjectDir.newFile("src/test/resources/application.properties").writeText(
            "spring.profiles.active=$template"
        )


        settingFile.writeText("")
        buildFile.writeText(
            """
            plugins {
                java
                id("com.github.aniaan.gradle-spring-profile")
            }
        """.trimIndent()
        )

        runner = GradleRunner.create()
            .forwardOutput()
            .withPluginClasspath()
            .withProjectDir(testProjectDir.root)
    }

    @Test
    fun `can run task`() {
        runner.withArguments("clean", "build", "-Pprofile=dev")
        runner.build()

        assertTrue {
            File(testProjectDir.root, "build/resources/test/application.yaml").readText().contains("active: dev")
        }

        assertTrue {
            File(testProjectDir.root, "build/resources/test/application.yml").readText().contains("active: dev")
        }

        assertTrue {
            File(testProjectDir.root, "build/resources/test/application.properties").readText()
                .contains("spring.profiles.active=dev")
        }

    }
}
