package com.kualagames.shared.utils

import com.kualagames.shared.database.databaseModule
import com.kualagames.shared.network.networkModule

val mergedModules = listOf(
    networkModule,
    databaseModule,
)