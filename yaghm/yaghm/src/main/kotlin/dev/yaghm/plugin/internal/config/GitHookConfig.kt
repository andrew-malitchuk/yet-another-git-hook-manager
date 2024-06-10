package dev.yaghm.plugin.internal.config

import dev.yaghm.plugin.common.model.FilePath
import dev.yaghm.plugin.internal.core.dsl.bash.Shebang
import dev.yaghm.plugin.internal.model.Command

/**
 * Represents the configuration for a Git hook script.
 *
 * This class provides properties for defining various aspects of a Git hook script:
 *  - `type`: The type of Git hook (e.g., `pre-commit`, `post-commit`).
 *  - `doFirst`: An optional command to execute before the main action.
 *  - `action`: The main command to be executed by the Git hook.
 *  - `doLast`: An optional command to execute after the main action.
 *  - `shebang`: The shebang line (interpreter directive) for the script (optional).
 *  - `filePath`: The path to the Git hook script file (optional).
 *
 * The class also provides a getter property named `gitHook` that constructs the final Git hook script
 * by concatenating the values of `doFirst`, `action`, and `doLast` with appropriate line breaks.
 *
 * @property type The type of Git hook (optional).
 * @property doFirst An optional command to execute before the main action.
 * @property action The main command to be executed by the Git hook.
 * @property doLast An optional command to execute after the main action.
 * @property shebang The shebang line (interpreter directive) for the script (optional).
 * @property filePath The path to the Git hook script file (optional).
 * @return [Command] The constructed Git hook script based on the configured properties.
 */
class GitHookConfig {
    var type: GitHookType? = null
    var doFirst: Command? = null
    var action: Command? = null
    var doLast: Command? = null
    var shebang: Shebang? = null
    var filePath: FilePath? = null

    val gitHook: Command
        get() {
            return Command(
"""
${doFirst?.value ?: ""}doFirst?.value ?: ""}
${action?.value ?: ""}action?.value ?: ""}
${doLast?.value ?: ""}doLast?.value ?: ""}
""".trimIndent().trim(),
            )
        }
}
