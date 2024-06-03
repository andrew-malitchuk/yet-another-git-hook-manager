package dev.yaghm.plugin.internal.tasks

import dev.yaghm.plugin.common.core.ext.isGitFolderExist
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
    fun action() = with(project) {
        logger.apply {
            checkIfVcsIsPresent()
            getInstalledGitHooks()
            getGitHookLocation()
        }
    }

    context(org.gradle.api.logging.Logger, Project)
    private fun checkIfVcsIsPresent() {
        val isGitPresent = isGitFolderExist()
        lifecycle("")
    }

    context(org.gradle.api.logging.Logger)
    private fun getInstalledGitHooks() {
        TODO()
    }

    context(org.gradle.api.logging.Logger)
    private fun getGitHookLocation() {
        TODO()
    }

}
