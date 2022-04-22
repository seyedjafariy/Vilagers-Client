package com.kualagames.shared.components.splash

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.rx.Observer
import com.kualagames.shared.components.DIComponent
import com.kualagames.shared.settings.SettingsStorage
import com.kualagames.shared.utils.asValue
import org.koin.core.component.get
import org.koin.core.component.inject
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
        labels(object : Observer<SplashComponent.State>{
            override fun onComplete() {}

            override fun onNext(value: SplashComponent.State) {
                val output = when (value) {
                    SplashComponent.State.Loading -> error("wrong label")
                    SplashComponent.State.Auth -> SplashComponent.Output.Auth
                    SplashComponent.State.Main -> SplashComponent.Output.Main
                }
                openNext(output)
            }
        })
    }
}