package com.kualagames.shared.components.splash

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.kualagames.shared.components.DIComponent
import com.kualagames.shared.utils.addTo
import com.kualagames.shared.utils.createdDisposables
import com.kualagames.shared.utils.onNextLabel
import org.koin.core.component.get
import org.koin.core.scope.Scope

interface SplashComponent {
    sealed interface State {
        object Loading : State
        object Auth : State
        object Main : State
    }

    sealed interface Output {
        object Auth : Output
        object Main : Output
    }
}

class SplashComponentImpl(
    componentContext: ComponentContext,
    parentScope : Scope,
    private val openNext : (SplashComponent.Output) -> Unit
) : DIComponent(componentContext, parentScope, listOf(splashModule)), SplashComponent{

    private val store : SplashStore = instanceKeeper.getStore {
        get<SplashStore>()
    }.apply {
        onNextLabel {
            val output = when (it) {
                SplashComponent.State.Loading -> error("wrong label")
                SplashComponent.State.Auth -> SplashComponent.Output.Auth
                SplashComponent.State.Main -> SplashComponent.Output.Main
            }
            openNext(output)
        } addTo createdDisposables
    }
}