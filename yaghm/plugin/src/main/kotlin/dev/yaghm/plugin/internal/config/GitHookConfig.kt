package dev.yaghm.plugin.internal.config

import dev.yaghm.plugin.common.model.FilePath
import dev.yaghm.plugin.internal.dsl.GitHookAction
import dev.yaghm.plugin.internal.dsl.GitHookType
import dev.yaghm.plugin.internal.dsl.gradle
import dev.yaghm.plugin.internal.dsl.preCommit

class GitHookConfig {
    var type: GitHookType? = null
    var doFirst: ((GitHookAction) -> Unit)? = null
    var action: ((GitHookAction) -> Unit)? = null
    var doLast: ((GitHookAction) -> Unit)? = null
    var filePath: FilePath? = null
}

fun configure(type: String, configure: GitHookConfig.() -> Unit): GitHookConfig {
    val gitHookConfig = GitHookConfig().apply {
        this.type = GitHookType.entries.firstOrNull { it.type == type }
    }
    gitHookConfig.configure()
    return gitHookConfig
}


fun foo() {
    configure("preCommit") {
        action = {

        }
        doLast = {
            gradle("ktlint")
        }
    }
    preCommit {
        action = {

        }
    }
}

