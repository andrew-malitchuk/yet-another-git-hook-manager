package dev.yaghm.plugin.internal.tasks

import dev.yaghm.plugin.common.core.ext.findGitHookFolder
import dev.yaghm.plugin.common.core.ext.isGitFolderExist
import dev.yaghm.plugin.internal.core.fs.Fs.Companion.getFilesWithExtension
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

/**
 * An abstract Gradle task for reviewing the current state of Git hooks in a project.
 *
 * This task helps you assess the "health" of your Git hooks by checking for their presence and location
 * within the project. It performs the following actions:
 *
 * 1. Checks if a VCS (Version Control System) is present by looking for the `.git` folder.
 * 2. If VCS is found, retrieves a list of all installed Git hook files and logs their names.
 * 3. If VCS is found, retrieves the absolute path of the Git hook folder and logs its location.
 *
 * This task relies on the project being a Git project and throws an `IllegalStateException` if VCS
 * is not found.
 *
 * @see isGitFolderExist
 * @see findGitHookFolder
 * @see getFilesWithExtension
 */
abstract class ReviewGitHookTask : DefaultTask() {

    init {
        group = "GitHook"
        description = "Check current GitHook \"health\""
    }

    @TaskAction
    fun action() =
        with(project) {
            logger.apply {
                checkIfVcsIsPresent()
                getInstalledGitHooks()
                getGitHookLocation()
            }
        }

    context(org.gradle.api.logging.Logger, Project)
    private fun checkIfVcsIsPresent() {
        val isGitPresent = isGitFolderExist()
        lifecycle("Is Git VCS installed: $isGitPresent")
    }

    context(org.gradle.api.logging.Logger, Project)
    private fun getInstalledGitHooks() {
        val isGitPresent = isGitFolderExist()
        require(isGitPresent)
        val gitHookFolder = project.findGitHookFolder()
        require(!gitHookFolder?.absolutePath.isNullOrEmpty())
        val list =
            getFilesWithExtension(
                gitHookFolder!!.absolutePath,
                "",
            )
        if (list.isEmpty()) {
            lifecycle("There is no installed GitHooks")
        } else {
            lifecycle("Installed GitHooks:")
            list.forEach {
                lifecycle("- ${it.name};")
            }
        }
    }

    context(org.gradle.api.logging.Logger, Project)
    private fun getGitHookLocation() {
        val isGitPresent = isGitFolderExist()
        require(isGitPresent)
        val gitHookFolder = project.findGitHookFolder()
        lifecycle("GitHook location: ${gitHookFolder?.absolutePath}")
    }
}
