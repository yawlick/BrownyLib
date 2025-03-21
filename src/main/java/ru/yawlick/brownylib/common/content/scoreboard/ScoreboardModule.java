package ru.yawlick.brownylib.common.content.scoreboard;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import ru.yawlick.brownylib.api.content.scoreboard.IScoreboardModule;
import ru.yawlick.brownylib.common.content.AbstractModule;

@Getter
public final class ScoreboardModule extends AbstractModule implements IScoreboardModule {
    private final Scoreboard scoreboard;

    public ScoreboardModule() {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        load();
    }

    @Override
    public void load() {}

    @Override
    public void unload() {}
}