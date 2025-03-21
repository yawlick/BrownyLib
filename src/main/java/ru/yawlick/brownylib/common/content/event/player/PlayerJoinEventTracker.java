package ru.yawlick.brownylib.common.content.event.player;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.command.Command;
import ru.yawlick.brownylib.api.content.entity.CustomEntity;
import ru.yawlick.brownylib.api.content.event.EventTracker;
import ru.yawlick.brownylib.common.content.entity.impl.CustomNPCEntity;

import java.util.HashMap;

public class PlayerJoinEventTracker implements EventTracker {
    @EventHandler(priority = EventPriority.HIGHEST)
    void onPlayerJoin(PlayerJoinEvent event) {
//        new Thread(() -> {
//            try {
//                //что бы не багалось, когда типы на старте заходят
//                BrownyLib.getInstance().getNetworkModule().setPVP(BrownyLib.getInstance().getNetworkModule().getLast());
//                Player player = event.getPlayer();
//                Bukkit.getScheduler().runTask(BrownyLib.getInstance(), new Runnable() {
//                    @Override
//                    public void run() {
//                        player.setGameMode(GameMode.ADVENTURE);
//                        player.getInventory().clear();
//                    }
//                });
//                String ip = player.getAddress().getAddress().getHostAddress();
//                BrownyLib.getInstance().getPlayerDataManager().loadData(player.getUniqueId());
//                HashMap<String, String> playerData = getPlayerDataLink(player);
//                boolean needToResetTitle = false;
////                log(playerData.get("IP4v") + " will equals? " + ip);
//                if(!playerData.get("password").equals("none") && playerData.get("IP4v").equals(ip)) {
////                    log(playerData.get("IP4v") + " equals " + ip);
//                    setPlayerData(player, "authorized", "true");
//                    player.sendTitle("§f§lCloudy§7MC", "§6Ваша сессия была восстановлена!", 10, 30, 10);
//                    teleport(player, SpecLocation.LOBBY);
//                } else {
//                    teleport(player, SpecLocation.LOGIN);
//                }
//                processVisual(player);
//                setPlayerData(player, "name", player.getName());
//
//                if(playerData.get("password").equals("none")) {
//                    player.sendTitle("§f§lCloudy§7MC", "§6Зарегистрируйтесь командой §e/register <пароль>§6!", 20, 200, 20);
//                    needToResetTitle = true;
//                } else if(playerData.get("authorized").equals("false")) {
//                    player.sendTitle("§f§lCloudy§7MC", "§6Авторизуйтесь командой §e/login <пароль>§6!", 20, 200, 20);
//                    needToResetTitle = true;
//                }
//
//                do {
//                    Thread.sleep(10);
//                } while (playerData != null && playerData.get("authorized").equalsIgnoreCase("false"));
//                teleport(player, SpecLocation.LOBBY);
//
//                if(needToResetTitle) {
//                    player.resetTitle();
//                }
//                setPlayerData(player, "IP4v", ip);
//
//                do {
//                    Thread.sleep(50);
//                } while (player.getClientBrandName() == null);
//                setPlayerData(player, "client-brand", player.getClientBrandName());
//                setPlayerData(player, "view-distance", player.getClientViewDistance());
//                for(Player player2 : Bukkit.getOnlinePlayers()) {
//                    player2.sendMessage(" §7[§a§l+§r§7] §a" + event.getPlayer().getName() + " §r§7" + String.format("(%s)", event.getPlayer().getClientBrandName()));
//                }
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
    }

    private void processVisual(Player player) {
        String commands = "";
        for(Command command : BrownyLib.getInstance().getCommandModule().getCommands()) {
            if(command.isRegistered()) {
                commands += command.getUsage() + " §7§l; §r§e";
            }
        }
        msg(player, " ");
        msg(player, " ");
        msg(player, "§6" + player.getName() + ", приветствуем вас на сервере §6BrownyLib§e!");
        msg(player, "§6Команды: §e"+ commands);
        msg(player, "§7§oТестовый сервер | by yawlick");
        msg(player, " ");
        msg(player, " ");

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if(player == null) {
                    return;
                }
                if(!player.isOnline()) {
                    return;
                }
            }
        };
        runnable.runTaskTimer(BrownyLib.getInstance(), 200, 200);

        for(CustomEntity entity : BrownyLib.getInstance().getCustomEntityModule().getEntities()) {
            if(entity instanceof CustomNPCEntity && entity.getEntity().getWorld().getPlayers().contains(player)) {
                ((CustomNPCEntity) entity).sendPackets(player);
            }
        }
    }
}