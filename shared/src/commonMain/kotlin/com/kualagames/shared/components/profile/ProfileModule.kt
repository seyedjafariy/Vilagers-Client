package com.kualagames.shared.components.profile

import org.koin.dsl.module

val profileModule = module {
    factory { ProfileRepository() }
}