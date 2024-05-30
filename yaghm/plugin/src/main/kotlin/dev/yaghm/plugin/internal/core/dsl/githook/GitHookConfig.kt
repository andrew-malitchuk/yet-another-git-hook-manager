package dev.yaghm.plugin.internal.core.dsl.githook

import dev.yaghm.plugin.common.model.FilePath
import dev.yaghm.plugin.internal.config.GitHookConfig
import dev.yaghm.plugin.internal.config.GitHookType
import dev.yaghm.plugin.internal.core.dsl.bash.Interpreter
import dev.yaghm.plugin.internal.core.dsl.bash.Shebang
import dev.yaghm.plugin.internal.model.Command


fun GitHookConfig.configure(type: String, block: GitHookConfig.() -> Unit): GitHookConfig {
    val gitHookConfig = GitHookConfig().apply {
        this.type = GitHookType.entries.firstOrNull { it.type == type }
    }
    require(gitHookConfig.type != null)
    gitHookConfig.block()
    this.type = gitHookConfig.type
    this.doFirst = gitHookConfig.doFirst
    this.action = gitHookConfig.action
    this.doLast = gitHookConfig.doLast
    this.shebang = gitHookConfig.shebang
    this.filePath = gitHookConfig.filePath
    return gitHookConfig
}

fun GitHookConfig.preCommit(block: GitHookConfig.() -> Unit): GitHookConfig {
    return this.configure(GitHookType.PRE_COMMIT.type!!, block)
}

fun GitHookConfig.prePush(block: GitHookConfig.() -> Unit): GitHookConfig {
    return this.configure(GitHookType.PRE_PUSH.type!!, block)
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

fun GitHookConfig.shebang(configure: () -> Shebang): GitHookConfig {
    val shebang = configure.invoke()
    return this.also {
        it.shebang = shebang
    }
}

fun GitHookConfig.useShebang(configure: () -> Interpreter): GitHookConfig {
    val interpreter = configure.invoke()
    return this.also {
        it.shebang = interpreter.value?.let { it1 -> Shebang(it1) }
    }
}

fun GitHookConfig.onFile(configure: () -> String): GitHookConfig {
    val filePath = configure.invoke()
    return this.also {
        it.filePath = FilePath(filePath)
    }
}