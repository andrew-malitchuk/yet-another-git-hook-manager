package dev.yaghm.plugin.internal.tasks

import dev.yaghm.plugin.common.core.ext.findGitHookFolder
import dev.yaghm.plugin.common.core.ext.getProjectPath
import dev.yaghm.plugin.common.core.ext.isGitFolderExist
import dev.yaghm.plugin.internal.config.GitHookConfig
import dev.yaghm.plugin.internal.core.dsl.bash.bash
import dev.yaghm.plugin.internal.core.dsl.bash.command
import dev.yaghm.plugin.internal.core.dsl.bash.shebang
import dev.yaghm.plugin.internal.core.dsl.githook.getFileName
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

    @TaskAction
    fun action() {

        logger.apply {
            checkIfVcsIsPresent(project)
            val bash = bash {
                gitHookConfig.get().shebang?.let { shebang(it) }
                command(gitHookConfig.get().gitHook)
            }


            val filename = gitHookConfig.get().type?.getFileName()
            require(!filename.isNullOrEmpty())

            Fs(filename) {
                if(project.isGitFolderExist()){
                    project.findGitHookFolder()?.absolutePath?.let{
                        createOrReplaceFile(directoryPath = it)
                        appendTextToFile(bash.fileContent)
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

}

