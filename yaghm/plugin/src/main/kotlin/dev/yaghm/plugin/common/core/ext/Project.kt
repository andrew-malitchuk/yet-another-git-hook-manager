package dev.yaghm.plugin.common.core.ext

import org.gradle.api.Project
import java.io.File


fun Project.getProjectPath(): String {
    return this.projectDir.absolutePath
}

fun Project.isGitFolderExist(): Boolean {
    val gitDir = File(projectDir, ".git")
    return gitDir.exists() && gitDir.isDirectory
}