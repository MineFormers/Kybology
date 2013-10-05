package de.mineformers.timetravel.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import de.mineformers.timetravel.entity.EntityRift;

/**
 * TimeTravel
 * 
 * GeneratorRift
 * 
 * @author PaleoCrafter, Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class GeneratorRift implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world,
            IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (random.nextInt(1000) <= 10) {
            EntityRift rift = new EntityRift(world);
            rift.posX = chunkX * 16 + random.nextInt(12);
            rift.posZ = chunkZ * 16 + random.nextInt(12);
            rift.posY = 10 + random.nextInt(128);
            rift.setType(0);
            rift.setEnergy(random.nextInt(200));
            rift.setMaximumEnergy(2000);
            rift.setSignature(20);
            world.spawnEntityInWorld(rift);
        }
    }

}
