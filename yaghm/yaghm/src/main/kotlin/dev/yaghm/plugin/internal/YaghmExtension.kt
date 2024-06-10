package dev.yaghm.plugin.internal

import dev.yaghm.plugin.internal.config.GitHookConfig
import org.gradle.api.Project

/**
 * An extension class for Yaghm Gradle plugin, allowing configuration of Git hooks.
 *
 * This extension provides a way to configure Git hooks within your Gradle project using the Yaghm plugin.
 * It offers a single property, `gitHook`, of type `GitHookConfig` that can be used to specify details
 * about the Git hook to be installed. You can further customize the Git hook configuration using
 * a lambda expression passed to the `gitHook` function.
 *
 * This extension is typically accessed through the `yaghmConfig` extension method available on your project
 * within the `build.gradle` file. Here's an example:
 *
 * ```gradle
 * yaghm {
 *     gitHook {
 *         type = "pre-push"
 *         // Other configurations for the Git hook
 *     }
 * }
 * ```
 *
 * @see GitHookConfig
 */
open class YaghmExtension {
    val gitHook = GitHookConfig()

    fun gitHook(configure: GitHookConfig.() -> Unit) = gitHook.configure()

    companion object {
        internal fun Project.yaghmConfig(): YaghmExtension = extensions.create("yaghm", YaghmExtension::class.java)
    }
}
