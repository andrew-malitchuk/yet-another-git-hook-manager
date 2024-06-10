package dev.yaghm.plugin.internal.core.dsl.githook

import dev.yaghm.plugin.common.core.ext.checkIfTaskPresent
import dev.yaghm.plugin.internal.config.GitHookConfig
import org.gradle.api.Project

/**
 * Constructs a Gradle command string prefixed with "./gradlew" for use within Git hook scripts.
 *
 * This function helps you easily create a string representing a Gradle command to be executed from a Git hook script.
 * It takes a `command` string as input, which should specify the desired Gradle task to be run.
 *
 * @param command The string representing the Gradle task or command to be executed (must not be blank).
 * @throws IllegalArgumentException if the provided command is blank.
 * @return A formatted string combining "./gradlew" with a space and the provided command.
 */
fun GitHookConfig.gradle(command: String): String {
    return "./gradlew $command"
}

/**
 * Constructs a Gradle command string prefixed with "./gradlew" to run a specific Gradle task from a Git hook script.
 *
 * This function helps you easily create a string representing a Gradle task execution within a Git hook script.
 * It takes a `task` string as input, which should specify the desired Gradle task to be run. This function first
 * verifies if the provided task is present in the current Gradle project. If the task is not found, an
 * `IllegalStateException` will be thrown. Otherwise, a formatted string combining "./gradlew" with a space
 * and the provided task is returned.
 *
 * @param task The string representing the Gradle task to be executed.
 * @throws IllegalStateException if the provided Gradle task is not found in the project.
 * @return A formatted string combining "./gradlew" with a space and the provided task.
 */
context(Project)
fun GitHookConfig.gradleTask(task: String): String {
    require(checkIfTaskPresent(task))
    return "./gradlew $task"
}
