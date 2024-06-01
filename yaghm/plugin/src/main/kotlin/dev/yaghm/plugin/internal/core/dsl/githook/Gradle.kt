package dev.yaghm.plugin.internal.core.dsl.githook

import dev.yaghm.plugin.common.core.ext.checkIfTaskPresent
import dev.yaghm.plugin.internal.config.GitHookConfig
import org.gradle.api.Project

fun GitHookConfig.gradle(command: String): String {
    return "./gradlew $command"
}

context(Project)
fun GitHookConfig.gradleTask(task: String): String {
    require(checkIfTaskPresent(task))
    return "./gradlew $task"
}
