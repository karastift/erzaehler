package com.karastift.erzaehler.domain.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class CharacterId(val id: String) {
    BLONDE_KID_GIRL("blonde_kid_girl"),
    BLONDE_MAN("blonde_man"),
    BLONDE_WOMAN("blonde_woman"),
    BLUE_HAIRED_KID_GIRL("blue_haired_kid_girl"),
    BLUE_HAIRED_WOMAN("blue_haired_woman"),
    BRIDE("bride"),
    BUSINESSMAN("businessman"),

    CHEF("chef"),
    DRACULA("dracula"),
    FARMER("farmer"),
    FIREFIGHTER("firefighter"),
    GOBLIN_KID("goblin_kid"),
    GOBLIN_MAN("goblin_man"),
    GOBLIN_WOMAN("goblin_woman"),

    JOKER("joker"),
    KNIGHT("knight"),
    KNIGHT_KID("knight_kid"),
    NINJA("ninja"),
    NUN("nun"),

    OLD_MAN("old_man"),
    OLD_WOMAN("old_woman"),
    POLICEMAN("policeman"),
    PUNK_KID_BOY("punk_kid_boy"),
    PUNK_WOMAN("punk_woman"),

    SOLDIER("soldier"),
    VIKING_KID_BOY("viking_kid_boy"),
    VIKING_MAN("viking_man"),
    VIKING_WOMAN("viking_woman");

    override fun toString(): String = id
}