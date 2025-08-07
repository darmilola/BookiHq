import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "1.9.21"
    id("com.google.gms.google-services")
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
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
            baseName = "composeApp"
            isStatic = true
        }
        iosTarget.compilations.getByName("main") {
            // The default file path is src/nativeInterop/cinterop/<interop-name>.def
            val nskeyvalueobserving by cinterops.creating
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
            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:33.1.1"))

            // Add the dependency for the Firebase Authentication library
            // When using the BoM, you don't specify versions in Firebase library dependencies
            implementation("com.google.firebase:firebase-auth")
            implementation("com.google.firebase:firebase-messaging")

            // Also add the dependency for the Google Play services library and specify its version
            implementation("com.google.android.gms:play-services-auth:21.2.0")

            implementation("com.google.android.gms:play-services-code-scanner:16.1.0")
            implementation("com.google.firebase:firebase-storage")
            implementation("com.google.auth:google-auth-library-oauth2-http:1.24.0")
            implementation("com.github.bumptech.glide:glide:4.16.0")
            implementation("co.paystack.android:paystack:3.3.0")
            implementation("co.paystack.android.design.widget:pinpad:1.0.8")
            implementation("androidx.lifecycle:lifecycle-common:2.8.0")
            implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
            implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            val voyagerVersion = "1.1.0-beta02"


            // Navigator
            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")

            // BottomSheetNavigator
            implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")

            // TabNavigator
            implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")

            // Transitions
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
            
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
            implementation("org.jetbrains.kotlin:kotlin-android-extensions-runtime:1.2.31")


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

            // Item Loading Placeholder
            implementation("com.eygraber:compose-placeholder-material3:1.0.8")

            //savedState
            api("io.github.hoc081098:kmp-viewmodel-savedstate:0.7.1")

            // pull to refresh
            implementation("dev.materii.pullrefresh:pullrefresh:1.3.0")

            val room_version = "2.7.0-alpha06"
            implementation("androidx.room:room-runtime:$room_version")
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation("co.touchlab:stately-common:2.0.5")
            implementation("uk.co.caprica:vlcj:4.7.0")
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "com.application.bookihq"
    compileSdk = 35


    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.application.bookihq"
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
            excludes += "/META-INF/INDEX.LIST"
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
    implementation(libs.androidx.sqlite.bundled.android)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraintlayout)
    ksp(libs.androidx.room.compiler)

}
