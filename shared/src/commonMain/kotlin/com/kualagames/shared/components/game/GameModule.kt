package com.kualagames.shared.components.game

import com.kualagames.shared.components.game.GameStore.Action.GameId
import org.koin.dsl.module

fun gameModule(action: GameId) = module {

    factory { GameStoreProvider(get(), get()).provide(action) }

    factory { GameManager(get(), get()) }

    factory { GameAPI(get()) }

}
