package com.kualagames.shared.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface MainComponent

class MainComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
) : MainComponent, ComponentContext by componentContext