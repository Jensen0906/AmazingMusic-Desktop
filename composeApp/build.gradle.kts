import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}
kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation("uk.co.caprica:vlcj:4.8.2")
            implementation("uk.co.caprica:vlcj-natives:4.8.0")
            implementation("org.openjfx:javafx-media:21:win")
            implementation("org.openjfx:javafx-base:21:win")
            implementation("org.openjfx:javafx-graphics:21:win")
            implementation("org.openjfx:javafx-swing:21:win")
            implementation("org.openjfx:javafx-controls:21:win")
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}


compose.desktop {
    application {
        mainClass = "org.may.amazingmusic.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.may.amazingmusic"
            packageVersion = "1.0.0"
        }
    }
}
