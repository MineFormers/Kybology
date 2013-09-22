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
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class GeneratorRift implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world,
            IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
	    if(random.nextInt(1000) <= 10) {
	    	EntityRift rift = new EntityRift(world);
	    	rift.posX = chunkX * 16 + 8;
	    	rift.posZ = chunkZ * 16 + 8;
	    	rift.posY = 10;
	    	world.spawnEntityInWorld(rift);
	    	System.out.println(rift.posX + ";" + rift.posZ);
	    }
    }

}
