package com.kualagames.shared.components.room_name_picker

import com.kualagames.shared.components.rooms.RoomsAPI
import org.koin.dsl.module

val roomNamePickerModule = module {

    factory { RoomsAPI(get()) }

    factory { RoomNamePickerStoreProvider(get(), get()).provide() }
}