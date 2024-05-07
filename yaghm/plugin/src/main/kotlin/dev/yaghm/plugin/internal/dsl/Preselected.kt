package dev.yaghm.plugin.internal.dsl

import dev.yaghm.plugin.internal.config.GitHookConfig

fun preCommit(configure: GitHookConfig.() -> Unit): GitHookConfig {
    return dev.yaghm.plugin.internal.config.configure(GitHookType.PRE_COMMIT.type ?: "", configure)
}

fun prePush(filePath: String): GitHookConfig {
    return GitHookConfig().apply {
        this.type = GitHookType.PRE_PUSH
        this.action = {
            GitHookAction().apply {
                command = filePath
            }
        }
    }
}