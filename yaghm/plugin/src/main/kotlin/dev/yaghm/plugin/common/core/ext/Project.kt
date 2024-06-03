package dev.yaghm.plugin.common.core.ext

import org.gradle.api.Project
import java.io.File

/**
 * Gets the absolute path of the project's root directory.
 *
 * This function retrieves the absolute path of the root directory associated with the current project.
 * The root directory is the top-level directory of your project, typically containing the `build.gradle.kts`
 * or `gradle.properties` file.
 *
 * @return The absolute path of the project's root directory as a `String`.
 */
fun Project.getProjectPath(): String {
    return this.rootDir.absolutePath
}

/**
 * Checks if a `.git` folder exists in the project's root directory.
 *
 * This function determines whether a directory named `.git` exists within the project's root directory.
 * The presence of a `.git` directory typically indicates that the project is under Git version control.
 *
 * @return `true` if the `.git` folder exists and is a directory, `false` otherwise.
 */
fun Project.isGitFolderExist(): Boolean {
    val gitDir = File(getProjectPath(), ".git")
    return gitDir.exists() && gitDir.isDirectory
}

/**
 * Searches for the nearest `.git` directory starting from the project's root directory.
 *
 * This function iterates upwards from the project's root directory, checking for the existence of a
 * directory named `.git` at each level. If a `.git` directory is found and is indeed a directory
 * (not a file), the function returns it as a `File` object. Otherwise, the function continues searching
 * up the directory hierarchy until reaching the root of the file system or encountering a directory
 * without a parent. If no `.git` directory is found, the function returns `null`.
 *
 * This method is useful for scenarios where the project might not be located directly within the
 * Git repository itself. For example, the project could be a subdirectory within a larger codebase
 * managed by Git.
 *
 * @return A `File` object representing the nearest `.git` directory if found, otherwise null.
 */
fun Project.findGitFolder(): File? {
    var currentDir = File(getProjectPath())

    while (currentDir.parentFile != null) {
        val gitDir = File(currentDir, ".git")
        if (gitDir.exists() && gitDir.isDirectory) {
            return gitDir
        }
        currentDir = currentDir.parentFile
    }

    return null
}

/**
 * Searches for the nearest Git hooks folder starting from the project's root directory.
 *
 * This function builds upon the `findGitFolder` function to locate the Git hooks directory.
 * It first calls `findGitFolder` to identify the nearest `.git` directory. If a `.git` directory is found,
 * the function constructs the path to the Git hooks folder by appending "/hooks" to the absolute path
 * of the `.git` directory and returns it as a `File` object. Otherwise, the function returns `null`.
 *
 * This method is useful for scenarios where you need to interact with Git hooks defined within the
 * Git repository.
 *
 * @return A `File` object representing the Git hooks folder if found, otherwise null.
 */
fun Project.findGitHookFolder(): File? {
    val gitFolder = findGitFolder()
    return gitFolder?.absolutePath?.plus("/hooks")?.let { File(it) }
}

/**
 * Checks if a specific task is registered with the current project.
 *
 * This function utilizes the `project.tasks` property to access the collection of registered tasks
 * for the project. It then uses the `findByName` method to search for a task with the provided `task`
 * name. If a matching task is found, the function returns `true`. Otherwise, it returns `false`.
 *
 * This method can be helpful for various purposes, such as conditionally executing code based on the
 * availability of specific tasks within the project.
 *
 * @param task The name of the task to search for (as a `String`).
 * @return `true` if the task exists, `false` otherwise.
 */
fun Project.checkIfTaskPresent(task: String): Boolean {
    return project.tasks.findByName(task) != null
}