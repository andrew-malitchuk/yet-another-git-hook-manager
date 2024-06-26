import dev.yaghm.plugin.internal.core.dsl.bash.Interpreter
import dev.yaghm.plugin.internal.core.dsl.githook.action
import dev.yaghm.plugin.internal.core.dsl.githook.configure
import dev.yaghm.plugin.internal.core.dsl.githook.doFirst
import dev.yaghm.plugin.internal.core.dsl.githook.doLast
import dev.yaghm.plugin.internal.core.dsl.githook.echo
import dev.yaghm.plugin.internal.core.dsl.githook.gradle
import dev.yaghm.plugin.internal.core.dsl.githook.onFile
import dev.yaghm.plugin.internal.core.dsl.githook.preCommit
import dev.yaghm.plugin.internal.core.dsl.githook.useShebang
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("io.github.andrew-malitchuk.yaghm")
}

android {
    namespace = "dev.yaghm.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.yaghm.sample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {}
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf("-Xcontext-receivers")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
    }
}

yaghm {
    gitHook {
        configure("pre-commit") {
            onFile {
                "foobar.txt"
            }
        }
        preCommit {
            doFirst {
                echo("hello world")
            }
            action {
                "echo \"main action\""
            }
            doLast {
                gradle("detekt")
            }
            useShebang {
                Interpreter.BASH
            }
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    androidTestImplementation(platform(libs.compose.bom))
    debugImplementation(libs.ui.tooling)
}
