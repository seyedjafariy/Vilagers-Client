package com.kualagames.shared.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed class RoleDTO {

    abstract val raw : String

    @Serializable
    sealed class VillagerRoles : RoleDTO() {

        @Serializable
        @SerialName("doctor")
        object Doctor : VillagerRoles() {
            @Transient
            override val raw: String = "doctor"
        }

        @Serializable
        @SerialName("seer")
        object Seer : VillagerRoles(){
            @Transient
            override val raw: String = "seer"
        }

        @Serializable
        @SerialName("villager")
        object Villager : VillagerRoles(){
            @Transient
            override val raw: String = "villager"
        }
    }

    @Serializable
    sealed class WerewolfRoles : RoleDTO() {

        @Serializable
        @SerialName("lecter")
        object Lecter : VillagerRoles(){
            @Transient
            override val raw: String = "lecter"
        }

        @Serializable
        @SerialName("godfather")
        object GodFather : VillagerRoles(){
            @Transient
            override val raw: String = "godfather"
        }

        @Serializable
        @SerialName("werewolf")
        object Werewolf : VillagerRoles(){
            @Transient
            override val raw: String = "werewolf"
        }
    }
}