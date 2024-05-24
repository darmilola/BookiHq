import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    kotlin("plugin.serialization") version "1.9.21"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation ("com.cloudinary:cloudinary-android:2.5.0")
            //Auth0
            implementation ("com.auth0.android:auth0:2.+")

            implementation("androidx.media3:media3-exoplayer:1.1.0")
            implementation("androidx.media3:media3-exoplayer-dash:1.1.0")
            implementation("androidx.media3:media3-ui:1.1.0")

        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            val voyagerVersion = "1.0.0-rc10"
            val compassVersion = "1.0.0"

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
            implementation("io.github.hoc081098:kmp-viewmodel-compose:0.6.1")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")


            implementation("dev.icerock.moko:mvvm-core:0.16.1") // only ViewModel, EventsDispatcher, Dispatchers.UI
            implementation("dev.icerock.moko:mvvm-flow:0.16.1") // api mvvm-core, CFlow for native and binding extensions
            implementation("dev.icerock.moko:mvvm-livedata:0.16.1") // api mvvm-core, LiveData and extensions
            implementation("dev.icerock.moko:mvvm-state:0.16.1") // api mvvm-livedata, ResourceState class and extensions
            implementation("dev.icerock.moko:mvvm-livedata-resources:0.16.1") // api mvvm-core, moko-resources, extensions for LiveData with moko-resources
            implementation("dev.icerock.moko:mvvm-flow-resources:0.16.1") // api mvvm-core, moko-resources, extensions for Flow with moko-resources

            // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-swing
            runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.8.0-RC2")

            //DI
            implementation(libs.koin.core)
            implementation("io.insert-koin:koin-test:3.5.0")

            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)

            //serialization
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.8")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.8")

            //reactiveX
            implementation ("com.badoo.reaktive:reaktive:2.1.0-beta01")
            implementation ("com.badoo.reaktive:reaktive-annotations:2.1.0-beta01")
            implementation ("com.badoo.reaktive:coroutines-interop:2.1.0-beta01")


            //Networking
            implementation(libs.ktor.client.core)
            implementation("io.ktor:ktor-client-logging:2.3.8")


            //Twilio
            implementation ("com.twilio.sdk:twilio:9.16.0")

            // peekaboo-ui
            implementation(libs.peekaboo.ui)

            // peekaboo-image-picker
            implementation(libs.peekaboo.image.picker)
            api("io.github.qdsfdhvh:image-loader:1.7.8")

            //datastore
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.1.1")
            implementation("com.russhwolf:multiplatform-settings-serialization:1.1.1")

            //paging
            implementation("app.cash.paging:paging-compose-common:3.3.0-alpha02-0.4.0")
            implementation("app.cash.paging:paging-common:3.3.0-alpha02-0.4.0")

            //SnackBar
            implementation("io.github.rizmaulana:compose-stacked-snackbar:1.0.4")

            //datetime
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0-RC.2")

            // Geolocation
            implementation("dev.jordond.compass:compass-geolocation:0.1.7")
            implementation("dev.jordond.compass:compass-geolocation-mobile:0.1.7")
            implementation("dev.jordond.compass:compass-geocoder:0.1.7")
            implementation("dev.jordond.compass:compass-geocoder-mobile:0.1.7")


        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.application.zazzy"
    compileSdk = 34


    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.application.zazzy"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["auth0Domain"] = "@string/com_auth0_domain"
        manifestPlaceholders["auth0Scheme"] = "demo"
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
            excludes += "/META-INF/DEPENDENCIES"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }

}
dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.i18n)
}

