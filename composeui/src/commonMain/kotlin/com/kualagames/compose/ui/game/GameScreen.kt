package com.kualagames.compose.ui.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.kualagames.shared.components.game.GameComponent
import com.kualagames.shared.components.game.GameComponent.State

@Composable
fun GameScreen(component: GameComponent) {
    val state by component.state.subscribeAsState()
    InternalGameScreen(state, component)
}

@Composable
fun InternalGameScreen(state: State, component: GameComponent) {

}