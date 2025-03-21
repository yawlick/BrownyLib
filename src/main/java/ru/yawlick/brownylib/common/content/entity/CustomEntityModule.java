package ru.yawlick.brownylib.common.content.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import lombok.Getter;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.entity.CustomEntity;
import ru.yawlick.brownylib.api.content.entity.ICustomEntityModule;
import ru.yawlick.brownylib.common.content.AbstractModule;
import ru.yawlick.brownylib.common.content.entity.event.impl.EntityClickEvent;
import ru.yawlick.brownylib.common.content.entity.event.impl.EntityTickEvent;
import ru.yawlick.brownylib.common.content.entity.impl.CustomNPCEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public final class CustomEntityModule extends AbstractModule implements ICustomEntityModule {
    @Getter private final ArrayList<CustomEntity> entities = new ArrayList<>();
    private final HashMap<UUID, Long> playersCooldown = new HashMap<>();
    private BukkitRunnable tickingRunnable;

    public CustomEntityModule() {
        load();
    }

    public void load() {
        startTicking();
        new BukkitRunnable() {
            @Override
            public void run() {
                for(CustomEntity entity : entities) {
                    if(entity instanceof CustomNPCEntity customNPCEntity) {
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer) player).getHandle().connection.sendPacket(new ClientboundPlayerInfoRemovePacket(Arrays.asList(customNPCEntity.getNPC().getUUID())));
                        }
                    }
                }
            }
        }.runTaskTimer(BrownyLib.getInstance(), 0 , 20);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(BrownyLib.getInstance(), PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                PacketContainer packet = event.getPacket();
                int entityId = packet.getIntegers().read(0);

                for(CustomEntity customEntity : entities) {
                    if(customEntity instanceof CustomNPCEntity customNPCEntity) {
                        if(customEntity.getEntity().getEntityId() == entityId) {
                            UUID uuid = player.getUniqueId();
                            if(playersCooldown.containsKey(uuid)) {
                                if(playersCooldown.get(uuid) < System.currentTimeMillis()) {
                                    customNPCEntity.fireEvent(new EntityClickEvent(customNPCEntity, player));
                                    playersCooldown.put(uuid, System.currentTimeMillis() + 200);
                                }
                            } else {
                                customNPCEntity.fireEvent(new EntityClickEvent(customNPCEntity, player));
                                playersCooldown.put(uuid, System.currentTimeMillis() + 200);
                            }
                            break;
                        }
                    }
                }
            }
        });
    }

    public void unload() {
        stopTicking();
    }

    private void startTicking() {
        tickingRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                if(entities.isEmpty())
                    return;
                for(CustomEntity entity : entities) {
                    entity.fireEvent(new EntityTickEvent(entity));
                }
            }
        };
        tickingRunnable.runTaskTimer(BrownyLib.getInstance(), 0, 1);
    }

    private void stopTicking() {
        if (tickingRunnable != null && !tickingRunnable.isCancelled()) {
            tickingRunnable.cancel();
        }
    }

    public CustomEntity get(int entityId) {
        for (CustomEntity entity : entities) {
            if (entity.getEntity() != null && entity.getEntity().getEntityId() == entityId) {
                return entity;
            }
        }
        return null;
    }

    public void addEntity(CustomEntity entity) {
        BrownyLib.getInstance().getLogger().info(String.format("The new '%s' entity has been successfully created", entity.getEntity().getType().name()));
        if(entity instanceof CustomNPCEntity) {
            BrownyLib.getInstance().getLogger().info(String.format("The new '%s' entity is a NPC", entity.getEntity().getType().name()));
        }
        entities.add(entity);
    }

    public void removeEntity(CustomEntity entity) {
        entities.remove(entity);
    }
}
