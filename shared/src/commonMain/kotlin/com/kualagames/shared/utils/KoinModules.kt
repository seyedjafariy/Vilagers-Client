package com.kualagames.shared.utils

import com.kualagames.shared.components.profile.profileModule
import com.kualagames.shared.database.databaseModule
import com.kualagames.shared.network.networkModule
import com.kualagames.shared.storages.storageModule

val mergedModules = listOf(
    networkModule,
    databaseModule,
    storageModule,
    profileModule,
)