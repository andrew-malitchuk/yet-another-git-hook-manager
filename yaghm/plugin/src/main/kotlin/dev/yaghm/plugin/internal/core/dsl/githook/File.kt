package dev.yaghm.plugin.internal.core.dsl.githook

import dev.yaghm.plugin.internal.config.GitHookConfig
import org.gradle.api.Project
import java.io.File

// context(Project)
fun GitHookConfig.local(
    project: Project,
    file: String,
): File {
    require(file.isNotBlank())
    val directory = project.buildFile.parent
    return File("$directory${File.separator}$file")
}
