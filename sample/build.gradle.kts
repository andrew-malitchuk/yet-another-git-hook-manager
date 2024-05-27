import dev.yaghm.plugin.internal.config.action
import dev.yaghm.plugin.internal.config.configure
import dev.yaghm.plugin.internal.config.doFirst
import dev.yaghm.plugin.internal.config.doLast
import dev.yaghm.plugin.internal.config.onFile
import dev.yaghm.plugin.internal.config.preCommit
import dev.yaghm.plugin.internal.config.shebang
import dev.yaghm.plugin.internal.config.useShebang
import dev.yaghm.plugin.internal.core.dsl.bash.Interpreter
import dev.yaghm.plugin.internal.core.dsl.githook.gradle
import dev.yaghm.plugin.internal.core.dsl.githook.gradleTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("dev.yaghm.plugin")
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
        getByName("debug") {
        }
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
//        configure("pre-commit") {
//            doFirst {
//                gradle("doFirst")
//            }
//            doLast {
//                "doLast"
//            }
//            useShebang {
//                Interpreter.BASH
//            }
//            onFile {
//                "foobar.txt"
//            }
//        }
        preCommit {
            doFirst {
//                gradleTask("doFirst")
                gradle("doFirst")
            }
            doLast {
                "doLast"
            }
            useShebang {
                Interpreter.BASH
            }
            onFile {
                "foobar.txt"
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
