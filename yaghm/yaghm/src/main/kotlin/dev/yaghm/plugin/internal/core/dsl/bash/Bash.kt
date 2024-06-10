package dev.yaghm.plugin.internal.core.dsl.bash

import dev.yaghm.plugin.internal.model.Command

/**
 * Represents a Bash script with its interpreter (shebang line) and content.
 *
 * This class allows you to define a Bash script by setting the `shebang` (interpreter)
 * and the `content` (commands) as strings. The `fileContent` getter property then combines
 * these elements into a complete Bash script string.
 *
 * @property shebang The optional shebang line specifying the script interpreter (e.g., `#!/bin/bash`).
 * @property content The content of the Bash script as a string.
 * @return [String] The complete Bash script content with shebang (if provided) and script commands.
 */
class Bash {
    var shebang: Shebang? = null
    var content: String = ""

    val fileContent: String
        get() {
            return """
                ${shebang?.interpreter}shebang?.interpreter}
                $contentcontent
                """.trimIndent().trim()
        }
}

/**
 * Configures the shebang (interpreter line) for this Bash script using a lambda expression.
 *
 * This function allows you to define the interpreter for the Bash script using a lambda.
 * The lambda expression should take no arguments and return a `Shebang` object (likely defined elsewhere).
 * The provided lambda will be invoked to obtain the shebang information, and the script's
 * `shebang` property will be set accordingly.
 *
 * @param configure A lambda expression that returns a `Shebang` object.
 * @return The modified Bash object with the configured shebang line.
 */
fun Bash.shebang(configure: () -> Shebang): Bash {
    val shebang = configure.invoke()
    return this.also {
        it.shebang = shebang
    }
}

/**
 * Sets the shebang (interpreter line) for this Bash script.
 *
 * This function provides a simpler way to set the shebang property by directly providing a `Shebang` object.
 * The `Shebang` object (likely defined elsewhere) specifies the interpreter for the script (e.g., `#!/bin/bash`).
 *
 * @param executeBy A `Shebang` object representing the script interpreter.
 * @return The modified Bash object with the configured shebang line.
 */
fun Bash.shebang(executeBy: Shebang): Bash {
    return this.also {
        it.shebang = executeBy
    }
}

/**
 * Adds a log statement to the content of this Bash script using a lambda expression.
 *
 * This function allows you to include log messages within your Bash script. The lambda expression takes no arguments
 * and returns a string representing the log message. The provided lambda will be invoked to obtain the log message,
 * and the message will be appended to the `content` property of the Bash script object, along with a newline character.
 *
 * @param configure A lambda expression that returns the log message as a string.
 * @return The modified Bash object with the added log statement.
 */
fun Bash.log(configure: () -> String): Bash {
    val command = configure.invoke()
    return this.also {
        it.content += "$command\n"
    }
}

/**
 * Adds a command to the content of this Bash script using a lambda expression.
 *
 * This function provides a more flexible way to define a command for the script. It takes a lambda expression
 * that takes no arguments and returns a `Command` object (likely defined elsewhere). The lambda will be invoked
 * to obtain the command, and the command value (assumed to be a string) will be appended to the `content` property
 * of the Bash script object, along with a newline character.
 *
 * @param configure A lambda expression that returns a `Command` object.
 * @return The modified Bash object with the added command.
 */
fun Bash.command(configure: () -> Command): Bash {
    val command = configure.invoke()
    return this.also {
        it.content += "${command.value ?: ""}\n"
    }
}

/**
 * Adds a command to the content of this Bash script.
 *
 * This function allows you to easily add a command to the script's content. It takes a `Command` object
 * (likely defined elsewhere) that represents the command to be added. The command value (assumed to be a string)
 * will be appended to the `content` property of the Bash script object, along with a newline character.
 *
 * @param command The Command object representing the command to be added.
 * @return The modified Bash object with the added command.
 */
fun Bash.command(command: Command): Bash {
    return this.also {
        it.content += "${command.value ?: ""}\n"
    }
}

/**
 * Creates and configures a new Bash script object.
 *
 * This function simplifies creating and building a Bash script. It takes a lambda expression
 * with a receiver of type `Bash`. Inside the lambda, you can call other functions like
 * `shebang`, `log`, and `command` (defined elsewhere) to configure the interpreter,
 * add log statements, and define the script commands.
 *
 * @param configure A lambda expression that configures the created Bash script object.
 * @return A new Bash object with the configured properties and content.
 */
fun bash(configure: Bash.() -> Unit): Bash {
    val bash = Bash()
    bash.configure()
    return bash
}
