package com.kualagames.shared.components.splash

import org.koin.dsl.module

internal val splashModule = module {
    factory {
        SplashStoreProvider(
            get(),
            get(),
        ).provide()
    }
}