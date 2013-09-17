package de.mineformers.timetravel.world.biome;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * TimeTravel
 * 
 * BiomeTest
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class BiomeTest extends BiomeGenBase {

	public final Material blockMaterial;

	public BiomeTest(int id) {
		super(id);

		this.blockMaterial = Material.water;
		this.minHeight = 0.1F;
		this.maxHeight = 0.6F;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.topBlock = ((byte) Block.grass.blockID);
		this.fillerBlock = ((byte) Block.dirt.blockID);
		this.setBiomeName("Test");
		

		/**
		 * this changes the water colour, its set to red now but ggole the java
		 * colours
		 **/
		this.waterColorMultiplier = 0x17E440;
	}
	
}
