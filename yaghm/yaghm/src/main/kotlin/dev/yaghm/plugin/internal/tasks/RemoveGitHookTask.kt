package dev.yaghm.plugin.internal.tasks

import dev.yaghm.plugin.common.core.ext.isGitFolderExist
import dev.yaghm.plugin.internal.config.GitHookConfig
import dev.yaghm.plugin.internal.core.fs.Fs
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

/**
 * An abstract Gradle task for removing a specific Git hook from a project.
 *
 * This task helps automate the process of removing a Git hook from your Gradle project. It allows
 * you to specify the Git hook to be removed by referencing its type through the `gitHookConfig` property.
 * This task also supports enabling debug logging for troubleshooting purposes.
 *
 * The task performs the following steps during execution:
 *
 * 1. Checks for the presence of a VCS (Version Control System) by looking for the `.git` folder.
 * 2. Extracts the Git hook type from the provided `gitHookConfig` property.
 * 3. Creates an `Fs` instance associated with the project and the Git hook type.
 * 4. Checks if a Git folder exists in the project.
 * 5. If a Git folder exists, attempts to delete the corresponding Git hook file based on the type.
 *
 * This task throws an `IllegalArgumentException` if the VCS is not found (no `.git` folder).
 *
 * @see GitHookConfig
 */
abstract class RemoveGitHookTask : DefaultTask() {
    init {
        group = "GitHook"
        description = "Remove specific GitHook"
    }

    @get:Input
    abstract val gitHookConfig: Property<GitHookConfig>

    @Option(description = Descriptions.DEBUG_DESCRIPTION, option = Params.DEBUG_OUTPUT)
    @get:Input
    var debugOutput: Boolean = false

    @TaskAction
    fun action() {
        logger.apply {
            checkIfVcsIsPresent(project)
            val filename = gitHookConfig.get().type?.type
            require(!filename.isNullOrEmpty())

            Fs(project, filename) {
                if (project.isGitFolderExist()) {
                    deleteFile()
                }
            }
        }
    }

    context(org.gradle.api.logging.Logger)
    private fun checkIfVcsIsPresent(project: Project) {
        if (!project.isGitFolderExist()) {
            lifecycle("\nError: perhaps, Git VCS is not applied (failed to find `.git` folder)")
            throw IllegalArgumentException("Failed to find `.git` folder")
        }
    }

    companion object {
        object Params {
            const val DEBUG_OUTPUT = "debugOutput"
        }

        object Descriptions {
            const val DEBUG_DESCRIPTION = "debugOutput"
        }
    }
}
