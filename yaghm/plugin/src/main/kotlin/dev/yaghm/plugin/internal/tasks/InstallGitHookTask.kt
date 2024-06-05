package dev.yaghm.plugin.internal.tasks

import dev.yaghm.plugin.common.core.ext.findGitHookFolder
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
 * An abstract Gradle task for installing Git hooks within a project.
 *
 * This task helps automate the process of installing Git hooks in your Gradle project. It
 * provides a flexible way to define the Git hook to be installed and its content source.
 *
 * The task performs the following steps during execution:
 *
 * 1. Checks for the presence of a VCS (Version Control System) by looking for the `.git` folder.
 * 2. Extracts the Git hook configuration details from the provided `gitHookConfig` property.
 * 3. Determines the source of truth for the Git hook content based on the presence of file path
 *    and DSL definition.
 * 4. Creates or replaces the corresponding Git hook file based on the Git hook type.
 * 5. Depending on the source of truth, it either clones the content from the specified file path
 *    or appends the provided DSL definition to the Git hook file.
 * 6. Attempts to make the created Git hook file executable.
 *
 * This task throws an `IllegalArgumentException` if:
 *
 * - VCS is not found (no `.git` folder).
 * - Both file path and DSL definition are provided (ambiguous source).
 * - The Git hook type is not found.
 *
 * @see GitHookConfig
 */
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

    private var sourceOfTruth: SOURCE_OF_TRUTH? = null

    @TaskAction
    fun action() {
        logger.apply {
            checkIfVcsIsPresent(project)

            val contentFile = gitHookConfig.get().filePath?.value
            val contentDsl = gitHookConfig.get().gitHook.value

            sourceOfTruth =
                when {
                    (contentFile.isNullOrEmpty() && !contentDsl.isNullOrEmpty()) ->
                        SOURCE_OF_TRUTH.DSL

                    (!contentFile.isNullOrEmpty() && contentDsl.isNullOrEmpty()) ->
                        SOURCE_OF_TRUTH.FILE

                    (!contentFile.isNullOrEmpty() && !contentDsl.isNullOrEmpty()) ->
                        SOURCE_OF_TRUTH.DSL

                    else -> throw IllegalArgumentException()
                }

            val filename = gitHookConfig.get().type?.type
            require(!filename.isNullOrEmpty())

            Fs(filename) {
                if (project.isGitFolderExist()) {
                    project.findGitHookFolder()?.absolutePath?.let {
                        createOrReplaceFile(directoryPath = it)
                        when (sourceOfTruth) {
                            SOURCE_OF_TRUTH.FILE -> contentFile?.let(::cloneFile)
                            SOURCE_OF_TRUTH.DSL -> contentDsl?.let(::appendTextToFile)
                            else -> throw IllegalArgumentException()
                        }

                        makeFileExecutable()
                    }
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

    enum class SOURCE_OF_TRUTH {
        FILE,
        DSL,
        NI,
    }
}
