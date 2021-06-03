package com.github.aniaan.spring.profile

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test


class GradleSpringProfilePluginTest {
    @Test fun `plugin replace task`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("com.github.aniaan.gradle-spring-profile")
    }
}
