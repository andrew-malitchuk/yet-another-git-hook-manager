package dev.yaghm.plugin.common.core.ext

import org.gradle.api.Project
import java.io.File


fun Project.getProjectPath(): String {
    return this.rootDir.absolutePath
}

fun Project.isGitFolderExist(): Boolean {
    val gitDir = File(getProjectPath(), ".git")
    return gitDir.exists() && gitDir.isDirectory
}

fun Project.findGitFolder(): File? {
    var currentDir = File(getProjectPath())

    while (currentDir.parentFile != null) {
        val gitDir = File(currentDir, ".git")
        if (gitDir.exists() && gitDir.isDirectory) {
            return gitDir
        }
        currentDir = currentDir.parentFile
    }

    return null
}

fun Project.findGitHookFolder(): File? {
    val gitFolder = findGitFolder()
    return gitFolder?.absolutePath?.plus("/hooks")?.let { File(it) }
}

fun Project.checkIfTaskPresent(task: String): Boolean {
    return project.tasks.findByName(task) != null
}