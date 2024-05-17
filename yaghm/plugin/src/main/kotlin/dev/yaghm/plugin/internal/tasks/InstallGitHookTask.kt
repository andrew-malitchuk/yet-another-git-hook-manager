package dev.yaghm.plugin.internal.tasks

import dev.yaghm.plugin.common.core.ext.findGitFolder
import dev.yaghm.plugin.common.core.ext.findGitHookFolder
import dev.yaghm.plugin.common.core.ext.getProjectPath
import dev.yaghm.plugin.common.core.ext.isGitFolderExist
import dev.yaghm.plugin.internal.config.GitHookConfig
import dev.yaghm.plugin.internal.core.fs.Fs
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class InstallGitHookTask : DefaultTask() {
    init {
        group = "GitHook"
        description = "Install specific GitHook"
    }

    @get:Input
    abstract val gitHookConfig: Property<GitHookConfig>

    @Option(description = Descriptions.DEBUG_DESCRIPTION, option = Params.DEBUG_OUTPUT)
    @get:Input
    var debugOutput: Boolean = false

    @TaskAction
    fun action() {

        logger.apply {
            lifecycle("foobar")
            Fs().apply {

            }
            lifecycle("${project.getProjectPath()}")
            lifecycle("${project.isGitFolderExist()}")
            lifecycle("${project.findGitFolder()}")
            lifecycle("${project.findGitHookFolder()}")
            checkIfVcsIsPresent(project)
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

