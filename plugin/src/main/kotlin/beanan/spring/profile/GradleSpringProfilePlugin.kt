package beanan.spring.profile

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.language.jvm.tasks.ProcessResources

class GradleSpringProfilePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.withType(ProcessResources::class.java) { processResources ->
            processResources.outputs.upToDateWhen { false }
            println("start match")
            processResources.eachFile {
                println("这里${it.name}")
            }
            processResources.filesMatching(
                "main/application.properties"
//                listOf(
//                    "application.yml",
//                    "application.yaml",
//                    "application.properties"
//                )
            ) { details ->
                println("start replace profile")
                val profile = getProfile(project = project)
                details.filter {
                    if (it.contains("@spring.active@")) {
                        it.replace("@spring.active@", profile)
                    } else {
                        it
                    }
                }
            }
        }

//        project.tasks.register("greeting") { task ->
//            task.dependsOn(project.tasks.withType(ProcessResources::class.java))
//            task.doLast {
//                println("Hello from plugin 'beanan.spring.profile.greeting'")
//            }
//        }

    }

    private fun getProfile(project: Project): String {
        var profile = "local"
        if (project.hasProperty("profile")) {
            profile = project.property("profile") as String
        }
        return profile
    }
}
