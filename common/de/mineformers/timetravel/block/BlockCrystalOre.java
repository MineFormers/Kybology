package de.mineformers.timetravel.block;

import de.mineformers.timetravel.lib.Strings;
import net.minecraft.block.material.Material;

/**
 * TimeTravel
 *
 * BlockCrystalOre
 *
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public class BlockCrystalOre extends BlockTT {

	public BlockCrystalOre(int id) {
		super(id, Material.rock, Strings.CRYSTAL_ORE_NAME);
	}

}
