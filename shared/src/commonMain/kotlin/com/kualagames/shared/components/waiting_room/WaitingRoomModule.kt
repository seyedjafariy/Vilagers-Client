package com.kualagames.shared.components.waiting_room

import com.kualagames.shared.components.rooms.RoomsAPI
import com.kualagames.shared.components.waiting_room.WaitingRoomStoreProvider.Action.Connection
import org.koin.dsl.module

fun waitingRoomModule(connection: Connection) = module {
    factory { RoomsAPI(get()) }

    factory { WaitingRoomStoreProvider(get(), get()).provide(connection) }
}