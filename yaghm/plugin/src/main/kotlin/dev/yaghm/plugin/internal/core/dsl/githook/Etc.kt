package dev.yaghm.plugin.internal.core.dsl.githook

import dev.yaghm.plugin.internal.config.GitHookConfig

fun GitHookConfig.echo(message: String): String {
    return "echo \"$message\""
}