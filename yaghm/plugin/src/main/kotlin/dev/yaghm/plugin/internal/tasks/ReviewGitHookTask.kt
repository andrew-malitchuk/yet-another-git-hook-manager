package dev.yaghm.plugin.internal.tasks

import dev.yaghm.plugin.common.core.ext.findGitHookFolder
import dev.yaghm.plugin.common.core.ext.isGitFolderExist
import dev.yaghm.plugin.internal.core.fs.Fs.Companion.getFilesWithExtension
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

/**
 * - git vcs;
 * - installed githooks;
 * - git hook location.
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
