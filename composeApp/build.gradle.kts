import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

  /*  // export correct artifact to use all classes of library directly from Swift
    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm-core:0.16.1")
            export("dev.icerock.moko:mvvm-livedata:0.16.1")
            export("dev.icerock.moko:mvvm-livedata-resources:0.16.1")
            export("dev.icerock.moko:mvvm-state:0.16.1")
        }
    }*/

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ZazzyApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            implementation("dev.icerock.moko:mvvm-livedata-material:0.16.1") // api mvvm-livedata, Material library android extensions
            implementation("dev.icerock.moko:mvvm-livedata-glide:0.16.1") // api mvvm-livedata, Glide library android extensions
            implementation("dev.icerock.moko:mvvm-livedata-swiperefresh:0.16.1") // api mvvm-livedata, SwipeRefreshLayout library android extensions
            implementation("dev.icerock.moko:mvvm-databinding:0.16.1") // api mvvm-livedata, DataBinding support for Android
            implementation("dev.icerock.moko:mvvm-viewbinding:0.16.1") // api mvvm-livedata, ViewBinding support for Android

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            val voyagerVersion = "1.0.0-rc10"

            // Multiplatform

            // Navigator
            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")

            // BottomSheetNavigator
            implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")

            // TabNavigator
            implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")

            // Transitions
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
            // compose multiplatform
            implementation("dev.icerock.moko:mvvm-compose:0.16.1") // api mvvm-core, getViewModel for Compose Multiplatform
            implementation("dev.icerock.moko:mvvm-flow-compose:0.16.1") // api mvvm-flow, binding extensions for Compose Multiplatform
            implementation("dev.icerock.moko:mvvm-livedata-compose:0.16.1") // api mvvm-livedata, binding extensions for Compose Multiplatform


            implementation("dev.icerock.moko:mvvm-core:0.16.1") // only ViewModel, EventsDispatcher, Dispatchers.UI
            implementation("dev.icerock.moko:mvvm-flow:0.16.1") // api mvvm-core, CFlow for native and binding extensions
            implementation("dev.icerock.moko:mvvm-livedata:0.16.1") // api mvvm-core, LiveData and extensions
            implementation("dev.icerock.moko:mvvm-state:0.16.1") // api mvvm-livedata, ResourceState class and extensions
            implementation("dev.icerock.moko:mvvm-livedata-resources:0.16.1") // api mvvm-core, moko-resources, extensions for LiveData with moko-resources
            implementation("dev.icerock.moko:mvvm-flow-resources:0.16.1") // api mvvm-core, moko-resources, extensions for Flow with moko-resources


        }
    }
}

android {
    namespace = "com.application.zazzy"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.application.zazzy"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

