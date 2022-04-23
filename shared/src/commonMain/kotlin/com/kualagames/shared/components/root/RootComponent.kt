package com.kualagames.shared.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.navigate
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.kualagames.shared.components.DIComponent
import com.kualagames.shared.components.MainComponent
import com.kualagames.shared.components.MainComponentImpl
import com.kualagames.shared.components.auth.AuthComponent
import com.kualagames.shared.components.auth.AuthComponentImpl
import com.kualagames.shared.components.splash.SplashComponent
import com.kualagames.shared.components.splash.SplashComponentImpl
import com.kualagames.shared.settings.SettingsStorage
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.context.loadKoinModules
import org.koin.core.scope.Scope

interface RootComponent {
    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        class Splash(val component: SplashComponent) : Child()
        class Main(val component: MainComponent) : Child()
        class Auth(val component: AuthComponent) : Child()
    }
}

class RootComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
) : DIComponent(componentContext, parentScope), RootComponent {

    private val router = router<Config, RootComponent.Child>(
        initialConfiguration = Config.Splash,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val routerState: Value<RouterState<*, RootComponent.Child>> = router.state

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child =
        when (config) {
            is Config.Main -> RootComponent.Child.Main(
                MainComponentImpl(
                    componentContext,
                )
            )
            is Config.Auth -> RootComponent.Child.Auth(
                AuthComponentImpl(
                    componentContext,
                    scope,
                )
            )
            is Config.Splash -> RootComponent.Child.Splash(
                SplashComponentImpl(
                    componentContext,
                    scope,
                    ::onSplashOutput,
                )
            )
        }

    private fun onSplashOutput(output: SplashComponent.Output) {
        val config = when (output) {
            SplashComponent.Output.Auth -> Config.Auth
            SplashComponent.Output.Main -> Config.Main
        }

        router.navigate { listOf(config) }
    }

    sealed interface Config : Parcelable {

        @Parcelize
        object Splash : Config

        @Parcelize
        object Main : Config

        @Parcelize
        object Auth : Config
    }
}



