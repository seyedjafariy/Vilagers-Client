package com.kualagames.shared.components.game.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class RoleDTO {

    @Serializable
    sealed class VillagerRoles : RoleDTO() {

        @Serializable
        @SerialName("doctor")
        object Doctor : VillagerRoles()

        @Serializable
        @SerialName("seer")
        object Seer : VillagerRoles()

        @Serializable
        @SerialName("villager")
        object Villager : VillagerRoles()
    }

    @Serializable
    sealed class WerewolfRoles : RoleDTO() {

        @Serializable
        @SerialName("lecter")
        object Lecter : VillagerRoles()

        @Serializable
        @SerialName("godfather")
        object GodFather : VillagerRoles()

        @Serializable
        @SerialName("werewolf")
        object Werewolf : VillagerRoles()
    }
}