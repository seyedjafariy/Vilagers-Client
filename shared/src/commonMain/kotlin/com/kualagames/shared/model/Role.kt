package com.kualagames.shared.model

sealed interface Role {
    val raw: String

    enum class WerewolfSide(
        override val raw: String
    ) : Role {
        Lecter("lecter"),
        GodFather("godfather"),
        Werewolf("werewolf"),
        ;
    }

    enum class VillagerSide(
        override val raw: String
    ) : Role {
        Doctor("doctor"),
        Seer("seer"),
        Villager("villager"),
        ;
    }

    companion object {
        private val values = WerewolfSide.values().toList<Role>() + VillagerSide.values()

        fun values(): List<Role> = values

        operator fun invoke(raw: String): Role =
            values.first { it.raw == raw }
    }
}