package dev.yaghm.plugin.internal.config

import dev.yaghm.plugin.common.model.FilePath
import dev.yaghm.plugin.internal.core.dsl.GitHookType
import dev.yaghm.plugin.internal.model.Command

// TODO: value classes
class GitHookConfig {
    var type: GitHookType? = null
    var doFirst: Command? = null
    var action: Command? = null
    var doLast: Command? = null
    var filePath: FilePath? = null
}

fun GitHookConfig.configure(type: String, configure: GitHookConfig.() -> Unit): GitHookConfig {
    val gitHookConfig = GitHookConfig().apply {
        this.type = GitHookType.entries.firstOrNull { it.type == type }
    }
    gitHookConfig.configure()
    this.type = gitHookConfig.type
    this.doFirst = gitHookConfig.doFirst
    this.action = gitHookConfig.action
    this.doLast = gitHookConfig.doLast
    this.filePath = gitHookConfig.filePath
    return gitHookConfig
}

fun GitHookConfig.doFirst(configure: () -> String): GitHookConfig {
    val command = configure.invoke()
    return this.also {
        it.doFirst = Command(command)
    }
}

fun GitHookConfig.action(configure: () -> String): GitHookConfig {
    val command = configure.invoke()
    return this.also {
        it.action = Command(command)
    }
}

fun GitHookConfig.doLast(configure: () -> String): GitHookConfig {
    val command = configure.invoke()
    return this.also {
        it.doLast = Command(command)
    }
}

fun GitHookConfig.onFile(configure: () -> String): GitHookConfig {
    val filePath = configure.invoke()
    return this.also {
        it.filePath = FilePath(filePath)
    }
}


fun foo() = GitHookConfig().run {
    configure("preCommit") {

    }
//    preCommit {
//        action = {
//
//        }
//    }
}

