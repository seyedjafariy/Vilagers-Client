import org.jetbrains.compose.compose

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        applicationId = "com.kualagames.vilagers"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packagingOptions {
        exclude("META-INF/*")
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":composeui"))
    implementation(compose.material)

    implementation(Deps.ArkIvanov.MVIKotlin.mvikotlin)
    implementation(Deps.ArkIvanov.MVIKotlin.mvikotlinMain)
    implementation(Deps.ArkIvanov.MVIKotlin.mvikotlinLogging)
    implementation(Deps.ArkIvanov.MVIKotlin.mvikotlinTimeTravel)

    implementation(Deps.ArkIvanov.Decompose.decompose)
    implementation(Deps.ArkIvanov.Decompose.extensionsCompose)

    implementation(Deps.AndroidX.AppCompat.appCompat)
    implementation(Deps.AndroidX.Activity.activityCompose)

    implementation(Deps.Squareup.SQLDelight.androidDriver)
    implementation(Deps.Squareup.okhttp)
    implementation(Deps.Squareup.loggingInterceptor)

    implementation(Deps.Koin.android)
    implementation(Deps.Koin.compose)

    implementation(Deps.Ktor.okHttp)
    implementation(Deps.Ktor.common)
}
