package dev.yaghm.plugin.internal.config

import dev.yaghm.plugin.common.model.FilePath
import dev.yaghm.plugin.internal.core.dsl.GitHookAction
import dev.yaghm.plugin.internal.core.dsl.GitHookType
import dev.yaghm.plugin.internal.core.dsl.gradle

// TODO: value classes
class GitHookConfig {
    var type: GitHookType? = null

    //    var doFirst: ((GitHookAction) -> String)? = null
    var firstly: String? = null
    var action: ((GitHookAction) -> Unit)? = null
    var doLast: ((GitHookAction) -> Unit)? = null
    var filePath: FilePath? = null
}

fun GitHookConfig.configure(type: String, configure: GitHookConfig.() -> Unit): GitHookConfig {
    val gitHookConfig = GitHookConfig().apply {
        this.type = GitHookType.entries.firstOrNull { it.type == type }
    }
    gitHookConfig.configure()
    this.type = gitHookConfig.type
    this.firstly = gitHookConfig.firstly
    return gitHookConfig
}

fun GitHookConfig.doFirst(configure: () -> String): GitHookConfig {
    val command = configure.invoke()
    return this.also {
        it.firstly = command
    }
}


fun foo() = GitHookConfig().run {
    configure("preCommit") {
        action = {

        }
        doLast = {
            gradle("ktlint")
        }
    }
//    preCommit {
//        action = {
//
//        }
//    }
}

