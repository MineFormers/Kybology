package de.mineformers.timetravel.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import de.mineformers.timetravel.lib.BlockIds;

/**
 * TimeTravel
 * 
 * GeneratorOres
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class GeneratorOres implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world,
            IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
            case 0:
                generateSurface(world, random, chunkX * 16, chunkZ * 16);
                break;
        }
    }

    private void generateSurface(World world, Random rand, int chunkX,
            int chunkZ) {
        for (int i = 0; i < 30; i++) {
            int randPosX = chunkX + rand.nextInt(16);
            int randPosY = 3 + rand.nextInt(22);
            int randPosZ = chunkZ + rand.nextInt(16);
            if (world.getBlockId(randPosX, randPosY - 1, randPosZ) == 1) {
                System.out.println(randPosX + ";" + randPosY + ";" + randPosZ);
                world.setBlock(randPosX, randPosY, randPosZ,
                        BlockIds.CRYSTAL_ORE, rand.nextInt(3), 1);
            }
        }
    }
}
