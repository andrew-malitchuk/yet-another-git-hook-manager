package dev.yaghm.plugin.internal.model

/**
 * Represents a command string used for scripting purposes (typically in Git hooks).
 *
 * This inline value class wraps a nullable String value representing a command. It leverages Kotlin's
 * inline class feature to improve performance by avoiding unnecessary object creation at runtime. The compiler
 * will treat instances of `Command` as the underlying String type wherever possible.
 *
 * @property value The String value representing the actual command. It can be null.
 */
@JvmInline
value class Command(
    val value: String?,
)
