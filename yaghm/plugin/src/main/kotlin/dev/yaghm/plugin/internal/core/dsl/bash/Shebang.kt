package dev.yaghm.plugin.internal.core.dsl.bash

/**
 * Represents the shebang line (interpreter directive) for a Bash script.
 *
 * This value class encapsulates the interpreter path specified in the shebang line
 * (e.g., `#!/bin/bash`). The `@JvmInline` annotation instructs the Kotlin compiler
 * to inline the value class when targeting the JVM backend, potentially improving performance.
 *
 * @property interpreter The path to the interpreter for the script (e.g., `/bin/bash`).
 */
@JvmInline
value class Shebang(
    val interpreter: String,
)

/**
 * Enumerates common shell interpreters used in shebang lines.
 *
 * This enum class represents various shell interpreters that can be specified in the shebang line (interpreter directive)
 * at the beginning of a script file. The shebang line typically follows the format `#!/path/to/interpreter`.
 *
 * @property value The string representation of the interpreter path for the shebang line (optional).
 */
enum class Interpreter(val value: String?) {
    SH("#!/bin/sh"),
    BASH("#!/bin/bash"),
    POWERSHELL("#!/bin/pwsh"),
}
