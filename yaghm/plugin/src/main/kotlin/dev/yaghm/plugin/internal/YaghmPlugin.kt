package dev.yaghm.plugin.internal

import dev.yaghm.plugin.internal.YaghmExtension.Companion.yaghmConfig
import dev.yaghm.plugin.internal.tasks.InstallGitHookTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class YaghmPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            val yaghmConfig = yaghmConfig()


            project.tasks.apply {
                register(INSTALL_GIT_HOOK, InstallGitHookTask::class.java) { task ->

                }
            }
        }
    }

    companion object {
        const val INSTALL_GIT_HOOK = "installGitHook"
    }

}