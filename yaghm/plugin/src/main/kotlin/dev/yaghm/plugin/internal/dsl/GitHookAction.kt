package dev.yaghm.plugin.internal.dsl

import dev.yaghm.plugin.internal.config.GitHookConfig

class GitHookAction {
    var command: String? = null
}

fun GitHookConfig.action(configure: (GitHookAction) -> Unit) {
    action = configure
}