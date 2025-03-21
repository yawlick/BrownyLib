package ru.yawlick.brownylib.common.content.world.generator;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.generator.CraftChunkData;
import org.bukkit.generator.ChunkGenerator;
import ru.yawlick.brownylib.api.IFastBrowny;

import java.util.Random;

public class EmptyWorldGenerator extends ChunkGenerator implements IFastBrowny {

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        return new CraftChunkData(world, new LevelChunk((Level) world, new ChunkPos(chunkX, chunkZ)));
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 102, 0);
    }
}
