package dev.yaghm.plugin.internal.core.dsl.githook

import dev.yaghm.plugin.internal.config.GitHookConfig

/**
 * Creates a string that resembles an echo command for the provided message.
 *
 * This function is useful for constructing echo commands within scripts or for testing purposes.
 * It simply returns a string in the format "echo \"message\"."
 *
 * @param message The message to be echoed.
 * @return A string representing the echo command.
 */
fun GitHookConfig.echo(message: String): String {
    return "echo \"$message\""
}