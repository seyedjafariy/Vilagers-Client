package com.kualagames.shared.components.game

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kualagames.shared.components.game.GameComponent.State
import com.kualagames.shared.components.game.GameStore.*
import com.kualagames.shared.components.game.model.GameCommandDTO
import com.kualagames.shared.components.game.model.Player
import com.kualagames.shared.components.game.model.toPlayer
import com.kualagames.shared.model.Role
import com.kualagames.shared.model.mappers.toDomain
import com.kualagames.shared.storages.UserInfoRepository
import com.kualagames.shared.utils.Log
import com.kualagames.shared.utils.exhaustive
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GameStoreProvider(
    private val userInfoRepository: UserInfoRepository,
    private val storeFactory: StoreFactory,
    private val gameManager: GameManager,
) {

    fun provide(action: Action.GameId): GameStore = object : GameStore, Store<Intent, State, Label> by storeFactory.create(
        name = "GameStore",
        initialState = State(),
        bootstrapper = SimpleBootstrapper(action),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ) {}

    sealed interface Message {
        object WaitingForPlayers : Message
        data class Players(val players: List<Player>) : Message
        data class RevealPlayerRole(val player: Player, val role: Role) : Message
        object DeletePlayerRoles : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Message, Label>() {

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.GameId -> {
                    observeTheGame(action.id)
                }
            }.exhaustive
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
        }

        private fun observeTheGame(gameId: String) {
            gameManager.joinAndObserve(gameId)
                .onEach {
                    try {
                        //handling commands shouldn't break the socket so any
                        //any exception should be silent and handled off the chain
                        handleNewCommand(it.command)
                    } catch (e: Exception) {
                        Log.e(e) { "observing game failed" }
                    }
                }
                .catch {
                    Log.e(it) { "observing game failed" }
                }
                .launchIn(scope)
        }

        private fun handleNewCommand(command: GameCommandDTO) {
            when (command) {
                is GameCommandDTO.AgoraToken -> {
                    Log.i("agora token received: ${command.token}")
                    //save the agora token
                    //and use it to connect to agora
                }
                is GameCommandDTO.WaitingForOthers -> {
                    dispatch(Message.WaitingForPlayers)
                }
                is GameCommandDTO.AllPlayers -> {
                    val players = command.users.map { it.toPlayer(it.user.id == userInfoRepository.user.id) }
                    dispatch(Message.Players(players))
                }
                is GameCommandDTO.RevealPlayerRole -> {
                    //Show player role in the state players list
                    dispatch(
                        Message.RevealPlayerRole(
                            command.player.toPlayer(command.player.user.id == userInfoRepository.user.id),
                            command.role.toDomain()
                        )
                    )
                }
                is GameCommandDTO.HidePlayersRoles -> {
                    dispatch(Message.DeletePlayerRoles)
                }
                GameCommandDTO.Speak -> TODO()
                GameCommandDTO.Silent -> TODO()
                GameCommandDTO.AllowVoting -> TODO()
                GameCommandDTO.BlockActions -> TODO()
                is GameCommandDTO.ShowVotes -> TODO()
                GameCommandDTO.ClearAllVotes -> TODO()
                GameCommandDTO.ShowDay -> TODO()
                GameCommandDTO.ShowNight -> TODO()
                GameCommandDTO.VoteDropped -> TODO()
                is GameCommandDTO.PlayerVotedOut -> TODO()
                is GameCommandDTO.PlayerKilled -> TODO()
                GameCommandDTO.AllowShooting -> TODO()
                GameCommandDTO.MakeYourMove -> TODO()
                is GameCommandDTO.RevealPlayerSide -> TODO()
                is GameCommandDTO.GameOver -> TODO()
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State = when (msg) {
            Message.WaitingForPlayers -> copy(waitingToStartTheGame = true)
            is Message.Players -> copy(players = msg.players)
            is Message.RevealPlayerRole -> {
                val updatedPlayers = this.players.toMutableList().apply {
                    val index = indexOfFirst { it.id == msg.player.id }.takeIf { it != -1 }
                        ?: error("player was not in the list ${msg.player}")

                    set(index, msg.player.copy(role = msg.role))
                }

                copy(players = updatedPlayers)
            }
            Message.DeletePlayerRoles -> copy(players = players.map { it.copy(role = null) })
        }
    }
}
