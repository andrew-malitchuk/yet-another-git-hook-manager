package dev.yaghm.plugin.internal

import dev.yaghm.plugin.internal.YaghmExtension.Companion.yaghmConfig
import dev.yaghm.plugin.internal.tasks.InstallGitHookTask
import dev.yaghm.plugin.internal.tasks.RemoveGitHookTask
import dev.yaghm.plugin.internal.tasks.ReviewGitHookTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * A Gradle plugin for Yaghm, providing functionalities to manage Git hooks within your project.
 *
 * This plugin simplifies the process of installing, removing, and reviewing Git hooks in your Gradle project.
 * It offers the following functionalities:
 *
 *  - **Configuration:** You can configure the Git hook settings (type, content source) through the `yaghmConfig`
 *    extension available on your project.
 *  - **Installation:** The plugin provides an `installGitHook` task that installs a Git hook based on the
 *    configuration provided in `yaghmConfig.gitHook`.
 *  - **Removal:** The plugin provides a `removeGitHook` task that removes a Git hook based on the configuration
 *    provided in `yaghmConfig.gitHook`.
 *  - **Review:** The plugin provides a `reviewGitHook` task that checks for the presence and location of Git hooks
 *    within your project.
 *
 * To use this plugin, apply it to your project in the `build.gradle` file:
 *
 * ```gradle
 * plugins {
 *     id 'your-yaghm-plugin-id' version 'your-plugin-version'
 * }
 * ```
 *
 * @see YaghmExtension
 * @see InstallGitHookTask
 * @see RemoveGitHookTask
 * @see ReviewGitHookTask
 */
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
