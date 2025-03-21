package ru.yawlick.brownylib.api;

import org.bukkit.entity.Player;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.common.util.PlayerUtil;

public interface IFastBrowny {
    default void log(String text) {
        BrownyLib.LOGGER.info(text);
    }

    default void log(String text, Object... obj) {
        BrownyLib.LOGGER.info(String.format(text, obj));
    }

    default void warn(String text) {
        BrownyLib.LOGGER.warning(text);
    }

    default void warn(String text, Object... obj) {
        BrownyLib.LOGGER.warning(String.format(text, obj));
    }

    default void msg(Player player, String text) {
        PlayerUtil.msg(player, text);
    }
}
