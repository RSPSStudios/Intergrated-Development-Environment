package com.javatar.plugin.shop.editor.shop.currency

enum class ShopCurrency(val itemId: Int = -1) {
    COINS(995),
    TOKKUL(6529),
    BONDS(13190),
    BLOOD_MONEY(13307),
    CREDITS(13190),
    VOTE_TICKETS(4067),
    WARRIOR_GUILD_TOKEN(8851),
    MARKS_OF_GRACE(11849),
    DONATOR_CREDITS(20527),  // --> can be donator credits
    SKILLING_CREDITS(20803),  // --> can be donator credits
    UNIDENTIFIED_MINERALS(21341),
    MAGE_ARENA_POINTS,
    GOLDEN_NUGGETS(12012),
    TOURNAMENT_TICKETS(5023),
    TASK_POINTS,
    SNOWBALL_POINTS,
    APPRECIATION_POINTS,
    REFUNDED_CREDITS,
    MOLCH_PEARLS(22820),
    EASTER_EGGS(11028),
    BOUNTY,
    PIECES_OF_EIGHT(8951),
    CASTLE_WARS_TICKETS(4067),
    ARCHERY_TICKETS(1464),
    TRADING_STICKS(6306),
    UNIDENTIFIED_MATERIALS(21341),
    MERMAIDS_TEARS(21656);

    override fun toString(): String {
        return name.toLowerCase().capitalize()
    }


}
