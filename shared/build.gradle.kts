plugins {
    id("multiplatform-setup")
    id("android-setup")
    id("kotlin-parcelize")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("MainDB") {
        packageName = "com.kualagames.vilagers.database"
    }
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(Deps.ArkIvanov.Decompose.decompose)
                implementation(Deps.ArkIvanov.Decompose.extensionsCompose)
                implementation(Deps.ArkIvanov.MVIKotlin.mvikotlin)
                implementation(Deps.ArkIvanov.MVIKotlin.mvikotlinExtensionsCoroutines)
                implementation(Deps.ArkIvanov.MVIKotlin.rx)
                implementation(Deps.JetBrains.Kotlin.coroutines)
                implementation(Deps.Squareup.SQLDelight.coroutines)
                implementation(Deps.Koin.koin)
                implementation(Deps.Touchlab.kermit)

                implementation(Deps.Ktor.common)
                implementation(Deps.Ktor.websocket)
                implementation(Deps.Ktor.Json.common)
                implementation(Deps.Ktor.Serialization.common)
                implementation(Deps.JetBrains.Kotlin.serialization)
                implementation(Deps.JetBrains.Kotlin.datetime)
            }
        }
    }
}