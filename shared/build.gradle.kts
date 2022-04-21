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
            }
        }
    }
}