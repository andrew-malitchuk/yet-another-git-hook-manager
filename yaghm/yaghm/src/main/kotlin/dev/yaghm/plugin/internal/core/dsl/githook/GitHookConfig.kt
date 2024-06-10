package dev.yaghm.plugin.internal.core.dsl.githook

import dev.yaghm.plugin.common.model.FilePath
import dev.yaghm.plugin.internal.config.GitHookConfig
import dev.yaghm.plugin.internal.config.GitHookType
import dev.yaghm.plugin.internal.core.dsl.bash.Interpreter
import dev.yaghm.plugin.internal.core.dsl.bash.Shebang
import dev.yaghm.plugin.internal.model.Command

/**
 * Configures a new GitHookConfig object using a lambda expression.
 *
 * This function allows you to define the configuration for a Git hook script. It takes a `type` string
 * (e.g., "pre-commit", "post-push") representing the Git hook type and a configuration block. The block
 * receives the current GitHookConfig object and allows you to set properties like `doFirst`, `action`,
 * `doLast`, `shebang`, and `filePath`. The provided block will be executed on a newly created
 * GitHookConfig object, and the final configuration will be returned.
 *
 * @param type The string representing the Git hook type (must match an existing GitHookType).
 * @param block The lambda expression that configures the GitHookConfig object.
 * @throws IllegalArgumentException if the provided type doesn't match a valid GitHookType.
 * @return A new GitHookConfig object with the configured properties.
 */
fun GitHookConfig.configure(
    type: String,
    block: GitHookConfig.() -> Unit,
): GitHookConfig {
    val gitHookConfig =
        GitHookConfig().apply {
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

/**
 * Configures a new GitHookConfig object specifically for the 'pre-commit' Git hook type.
 *
 * This function is a shortcut for configuring a GitHookConfig object to be used as a 'pre-commit' hook.
 * It takes a lambda expression that allows you to set properties like `doFirst`, `action`,
 * `doLast`, `shebang`, and `filePath` for the 'pre-commit' hook script.
 *
 * @param block The lambda expression that configures the GitHookConfig object for the 'pre-commit' hook.
 * @return A new GitHookConfig object configured for the 'pre-commit' Git hook.
 */
fun GitHookConfig.preCommit(block: GitHookConfig.() -> Unit): GitHookConfig {
    return this.configure(GitHookType.PRE_COMMIT.type!!, block)
}

/**
 * Configures a new GitHookConfig object specifically for the 'pre-push' Git hook type.
 *
 * This function is a shortcut for configuring a GitHookConfig object to be used as a 'pre-push' hook.
 * It takes a lambda expression that allows you to set properties like `doFirst`, `action`,
 * `doLast`, `shebang`, and `filePath` for the 'pre-push' hook script.
 *
 * @param block The lambda expression that configures the GitHookConfig object for the 'pre-push' hook.
 * @return A new GitHookConfig object configured for the 'pre-push' Git hook.
 */
fun GitHookConfig.prePush(block: GitHookConfig.() -> Unit): GitHookConfig {
    return this.configure(GitHookType.PRE_PUSH.type!!, block)
}

/**
 * Adds a command to be executed before the main script in the 'doFirst' block of the Git hook script using a lambda expression.
 *
 * This function allows you to define a command to be executed before the main script logic begins.
 * The lambda expression should return a string representing the command to be added. The provided command
 * will be converted to a `Command` object and appended to the existing `doFirst` block (list of commands)
 * within the current GitHookConfig object.
 *
 * @param configure A lambda expression that returns a string representing the command to be added to the 'doFirst' block.
 * @return The modified GitHookConfig object with the added command to the 'doFirst' block.
 */
fun GitHookConfig.doFirst(configure: () -> String): GitHookConfig {
    val command = configure.invoke()
    return this.also {
        it.doFirst = Command(command)
    }
}

/**
 * Sets the main script (action) to be executed during the Git hook using a lambda expression.
 *
 * This function allows you to define the core script logic for the Git hook. The lambda expression should
 * return a string representing the script commands to be executed. The provided script will be converted
 * to a `Command` object (or potentially stored directly as a string) and assigned to the `action` property
 * of the current GitHookConfig object.
 *
 * @param configure A lambda expression that returns the script commands (as a string) for the Git hook.
 * @return The modified GitHookConfig object with the configured action script.
 */
fun GitHookConfig.action(configure: () -> String): GitHookConfig {
    val command = configure.invoke()
    return this.also {
        it.action = Command(command)
    }
}

/**
 * Adds a command to be executed after the main script in the 'doLast' block of the Git hook script using a lambda expression.
 *
 * This function allows you to define a command to be executed after the main script logic has finished.
 * The lambda expression should return a string representing the command to be added. The provided command
 * will be converted to a `Command` object and appended to the existing `doLast` block (list of commands)
 * within the current GitHookConfig object.
 *
 * @param configure A lambda expression that returns a string representing the command to be added to the 'doLast' block.
 * @return The modified GitHookConfig object with the added command to the 'doLast' block.
 */
fun GitHookConfig.doLast(configure: () -> String): GitHookConfig {
    val command = configure.invoke()
    return this.also {
        it.doLast = Command(command)
    }
}

/**
 * Configures the shebang (interpreter line) for the Git hook script using a lambda expression.
 *
 * This function allows you to define the interpreter for the Git hook script by directly providing a `Shebang` object
 * using a lambda expression. The provided lambda should return a `Shebang` object representing the desired interpreter
 * (e.g., `Shebang("#!/bin/bash")`). This `Shebang` object will be assigned to the `shebang` property of the current
 * GitHookConfig object.
 *
 * @param configure A lambda expression that returns a `Shebang` object for the script interpreter.
 * @return The modified GitHookConfig object with the configured shebang.
 */
fun GitHookConfig.shebang(configure: () -> Shebang): GitHookConfig {
    val shebang = configure.invoke()
    return this.also {
        it.shebang = shebang
    }
}

/**
 * Configures the shebang (interpreter line) for the Git hook script using a lambda expression.
 *
 * This function allows you to define the interpreter for the Git hook script dynamically using a lambda expression.
 * The lambda expression should return an `Interpreter` object (e.g., `SH`, `BASH`, `POWERSHELL`). The provided
 * interpreter will be used to construct a `Shebang` object and assigned to the `shebang` property of the current
 * GitHookConfig object. If the lambda returns null, no shebang line will be included in the script.
 *
 * @param configure A lambda expression that returns an optional `Interpreter` object for the script.
 * @return The modified GitHookConfig object with the configured shebang (if provided).
 */
fun GitHookConfig.useShebang(configure: () -> Interpreter): GitHookConfig {
    val interpreter = configure.invoke()
    return this.also {
        it.shebang = interpreter.value?.let { it1 -> Shebang(it1) }
    }
}

/**
 * Sets the file path for the Git hook script using a lambda expression.
 *
 * This function allows you to define the path to the Git hook script file dynamically using a lambda expression.
 * The lambda expression should return a string representing the file path. The provided path will be converted
 * to a `FilePath` object and assigned to the `filePath` property of the current GitHookConfig object.
 *
 * @param scriptPathProvider A lambda expression that returns the path to the Git hook script file (as a string).
 * @return The modified GitHookConfig object with the configured file path.
 */
fun GitHookConfig.onFile(configure: () -> String): GitHookConfig {
    val filePath = configure.invoke()
    return this.also {
        it.filePath = FilePath(filePath)
    }
}
