package com.kualagames.shared.components.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.kualagames.shared.components.DIComponent
import com.kualagames.shared.components.login.LoginComponent
import com.kualagames.shared.components.login.LoginComponentImpl
import org.koin.core.scope.Scope

interface AuthComponent {

    val routerState: Value<RouterState<*, Child>>

    sealed interface Child {
        class Login(val component: LoginComponent) : Child
        object Register : Child
    }
}

class AuthComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
) : DIComponent(componentContext, parentScope, listOf(authModule)), AuthComponent {

    private val router = router(
        initialConfiguration = Config.Login as Config,
        handleBackButton = true,
        childFactory = ::childFactory
    )

    override val routerState: Value<RouterState<*, AuthComponent.Child>> =
        router.state

    private fun childFactory(
        config: Config,
        componentContext: ComponentContext
    ): AuthComponent.Child =
        when (config) {
            is Config.Login -> AuthComponent.Child.Login(LoginComponentImpl(componentContext, scope) {
                router.push(Config.Register)
            })
            is Config.Register -> AuthComponent.Child.Register
        }

    sealed interface Config : Parcelable {
        @Parcelize
        object Login : Config

        @Parcelize
        object Register : Config
    }
}