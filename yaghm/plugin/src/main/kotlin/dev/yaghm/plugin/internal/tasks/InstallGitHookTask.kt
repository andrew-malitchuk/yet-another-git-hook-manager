package dev.yaghm.plugin.internal.tasks

import dev.yaghm.plugin.internal.config.GitHookConfig
import org.gradle.api.DefaultTask
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

