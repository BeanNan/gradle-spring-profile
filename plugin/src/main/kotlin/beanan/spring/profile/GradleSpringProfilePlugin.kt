package beanan.spring.profile

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.language.jvm.tasks.ProcessResources

class GradleSpringProfilePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.withType(ProcessResources::class.java) { processResources ->
            processResources.outputs.upToDateWhen { false }
            processResources.filesMatching(
                listOf(
                    "application.yml",
                    "application.yaml",
                    "application.properties"
                )
            ) { details ->
                val profile = getProfile(project = project)
                println("${details.name}: start replace to $profile")
                details.filter {
                    if (it.contains("@spring.active@")) {
                        it.replace("@spring.active@", profile)
                    } else {
                        it
                    }
                }
            }
        }

    }

    private fun getProfile(project: Project): String {
        var profile = "local"
        if (project.hasProperty("profile")) {
            profile = project.property("profile") as String
        }
        return profile
    }
}
