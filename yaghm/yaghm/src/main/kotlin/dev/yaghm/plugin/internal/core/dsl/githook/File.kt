package dev.yaghm.plugin.internal.core.dsl.githook

import dev.yaghm.plugin.internal.config.GitHookConfig
import org.gradle.api.Project
import java.io.File

/**
 * Gets the absolute path of a file within the local project directory.
 *
 * This function takes a `Project` object and a relative file path as arguments. It retrieves
 * the build file directory from the `Project` object and constructs the absolute path by combining
 * the directory path, a file separator, and the provided relative file path.
 *
 * @param project The project object representing the current project.
 * @param file The relative path of the file within the project directory (must not be blank).
 * @throws IllegalArgumentException if the provided file path is blank.
 * @return A `File` object representing the absolute path of the specified file.
 */
// context(Project)
fun GitHookConfig.local(
    project: Project,
    file: String,
): File {
    require(file.isNotBlank())
    val directory = project.buildFile.parent
    return File("$directory${File.separator}$file")
}
