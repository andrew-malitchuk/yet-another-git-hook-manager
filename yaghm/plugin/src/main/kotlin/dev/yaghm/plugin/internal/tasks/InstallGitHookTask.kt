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
 * https://medium.com/grandcentrix/how-to-debug-gradle-plugins-with-intellij-eef2ef681a7b
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
