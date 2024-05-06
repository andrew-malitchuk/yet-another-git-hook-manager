package dev.yaghm.plugin.internal

import dev.yaghm.plugin.internal.config.GitHookConfig
import org.gradle.api.Project

class YaghmExtension {

    val gitHook = GitHookConfig()

    fun gitHook(configure: GitHookConfig.() -> Unit) = gitHook.configure()

    companion object {
        internal fun Project.yaghmConfig(): YaghmExtension =
            extensions.create("yaghm", YaghmExtension::class.java)
    }
}