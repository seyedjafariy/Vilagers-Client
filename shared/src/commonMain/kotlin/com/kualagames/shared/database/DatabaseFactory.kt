package com.kualagames.shared.database

import com.kualagames.vilagers.database.MainDB
import com.squareup.sqldelight.db.SqlDriver

object DatabaseFactory {

    fun create(driver : SqlDriver) : MainDB =
        MainDB(driver)
}