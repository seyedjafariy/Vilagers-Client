package com.kualagames.shared.components.rooms

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.kualagames.shared.components.DIComponent
import com.kualagames.shared.utils.asValue
import org.koin.core.component.get
import org.koin.core.scope.Scope

interface RoomsComponent {

    val state: Value<State>

    data class State(
        val loading: Boolean = false,
        val user: String = "",
    )
}

class RoomsComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
) : DIComponent(componentContext, parentScope, listOf(roomsModule)), RoomsComponent {

    private val store: RoomsStore = instanceKeeper.getStore {
        get()
    }

    override val state: Value<RoomsComponent.State> =
        store.asValue()
}