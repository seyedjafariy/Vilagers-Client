package com.kualagames.shared.components.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.router
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface AuthComponent {

    sealed interface Child {
        object Login : Child
        object Register : Child
    }
}

class AuthComponentImpl(
    componentContext: ComponentContext,
) : AuthComponent, ComponentContext by componentContext {

    private val router = router(
        initialConfiguration = Config.Login,
        handleBackButton = true,
        childFactory = ::childFactory
    )


    private fun childFactory(config : Config, componentContext: ComponentContext) : AuthComponent.Child =
        when (config) {
            Config.Login -> AuthComponent.Child.Login
            Config.Register -> AuthComponent.Child.Login
        }

    sealed interface Config : Parcelable {
        @Parcelize
        object Login : Config

        @Parcelize
        object Register : Config
    }
}