package dev.yaghm.plugin.internal

import dev.yaghm.plugin.internal.YaghmExtension.Companion.yaghmConfig
import dev.yaghm.plugin.internal.tasks.ReviewGitHookTask
import dev.yaghm.plugin.internal.tasks.InstallGitHookTask
import dev.yaghm.plugin.internal.tasks.RemoveGitHookTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class YaghmPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            val yaghmConfig = yaghmConfig()

            project.tasks.apply {
                register(Tasks.INSTALL_GIT_HOOK, InstallGitHookTask::class.java) { task ->
                    yaghmConfig.gitHook.apply {
                        task.group = Group.GROUP_GIT_HOOK
                        task.description = Description.DESCRIPTION_INSTALL_GIT_HOOK
                        task.gitHookConfig.set(this)
                    }
                }
                register(Tasks.REMOVE_GIT_HOOK, RemoveGitHookTask::class.java) { task ->
                    yaghmConfig.gitHook.apply {
                        task.group = Group.GROUP_GIT_HOOK
                        task.description = Description.DESCRIPTION_REMOVE_GIT_HOOK
                        task.gitHookConfig.set(this)
                    }
                }
                register(Tasks.REVIEW_GIT_HOOK, ReviewGitHookTask::class.java) { task ->
                    yaghmConfig.gitHook.apply {
                        task.group = Group.GROUP_GIT_HOOK
                        task.description = Description.DESCRIPTION_REVIEW_GIT_HOOK
                    }
                }
            }
        }
    }

    companion object {
        object Tasks {
            const val INSTALL_GIT_HOOK = "installGitHook"
            const val REMOVE_GIT_HOOK = "removeGitHook"
            const val REVIEW_GIT_HOOK = "reviewGitHook"
        }

        object Group {
            const val GROUP_GIT_HOOK = "GitHook"
        }

        object Description {
            const val DESCRIPTION_INSTALL_GIT_HOOK = "installGitHook"
            const val DESCRIPTION_REMOVE_GIT_HOOK = "removeGitHook"
            const val DESCRIPTION_REVIEW_GIT_HOOK = "reviewGitHook"
        }
    }
}
