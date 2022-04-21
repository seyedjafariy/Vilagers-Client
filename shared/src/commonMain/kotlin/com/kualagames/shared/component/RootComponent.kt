package com.kualagames.shared.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface RootComponent {
    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        class Main(val component: MainComponent) : Child()
    }
}

class RootComponentImpl(
    componentContext: ComponentContext,
    private val storeFactory : StoreFactory
) : RootComponent, ComponentContext by componentContext {

    private val router = router<Config, RootComponent.Child>(
            initialConfiguration = Config.Main,
            handleBackButton = true,
            childFactory = ::createChild
        )


    override val routerState: Value<RouterState<*, RootComponent.Child>> = router.state

    private fun createChild(config: Config, componentContext: ComponentContext) : RootComponent.Child =
        when (config) {
            is Config.Main -> RootComponent.Child.Main(MainComponentImpl(componentContext, storeFactory))
        }

    sealed interface Config : Parcelable {

        @Parcelize
        object Main : Config
    }
}



