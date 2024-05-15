package dev.yaghm.plugin.internal.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class InstallGitHookTask : DefaultTask() {
    init {
        group = "GitHook"
        description = "Install specific GitHook"
    }

    @TaskAction
    fun action() {

    }

}

