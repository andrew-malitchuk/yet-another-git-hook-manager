package dev.yaghm.plugin.internal.core.dsl

import dev.yaghm.plugin.internal.config.GitHookConfig

fun GitHookConfig.gradle(command: String): String {
    return "./gradlew $command"
}