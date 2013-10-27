package de.mineformers.kybology.timetravel.travelling.timemachine;

import net.minecraft.tileentity.TileEntity;
import de.mineformers.kybology.timetravel.network.packet.PacketTimeMachineUpdate;
import de.mineformers.kybology.timetravel.tileentity.TileTimeMachine;

/**
 * Kybology
 * 
 * TMPartBase
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TMPartBase extends TimeMachinePart {

	public TMPartBase(TileEntity parent) {
		super(TimeMachinePart.TYPE_BASE, parent);
	}

	public void validateMultiblock() {
		if (!this.getWorld().isRemote) {
			int x = (int) this.getPos().x;
			int y = (int) this.getPos().y;
			int z = (int) this.getPos().z;
			int foundStairs = 0;
			int foundPillars = 0;
			int foundPanels = 0;
			for (int xOff = -1; xOff <= 1; xOff++) {
				for (int zOff = -1; zOff <= 1; zOff++) {
					if (this.getWorld().getBlockTileEntity(x + xOff, y,
					        z + zOff) != null) {
						if (this.getWorld().getBlockTileEntity(x + xOff, y,
						        z + zOff) instanceof TileTimeMachine) {
							TileTimeMachine tile = (TileTimeMachine) this
							        .getWorld().getBlockTileEntity(x + xOff, y,
							                z + zOff);

							if (tile.getTypeMeta() == TimeMachinePart.TYPE_PILLAR
							        && Math.abs(xOff) > 0 && Math.abs(zOff) > 0)
								foundPillars += 1;
							if (tile.getTypeMeta() == TimeMachinePart.TYPE_PANEL
							        && Math.abs(xOff) > 0 && Math.abs(zOff) > 0)
								foundPanels += 1;
							if (tile.getTypeMeta() == TimeMachinePart.TYPE_STAIRS
							        && ((xOff == 0 && Math.abs(zOff) > 0) || (zOff == 0 && Math
							                .abs(xOff) > 0)))
								foundStairs += 1;
						}
					}
				}
			}

			if (foundStairs == 4 && foundPillars == 3 && foundPanels == 1) {
				for (int xOff = -1; xOff <= 1; xOff++) {
					for (int zOff = -1; zOff <= 1; zOff++) {
						if (this.getWorld().getBlockTileEntity(x + xOff, y,
						        z + zOff) != null) {
							if (this.getWorld().getBlockTileEntity(x + xOff, y,
							        z + zOff) instanceof TileTimeMachine) {
								TileTimeMachine tile = (TileTimeMachine) this
								        .getWorld().getBlockTileEntity(
								                x + xOff, y, z + zOff);
								if (tile.getPart() != null) {
									if (tile.getPart().getTypeMeta() == TimeMachinePart.TYPE_PANEL)
										((TMPartPanel) tile.getPart())
										        .setBasePosition(x, y, z);
									tile.getPart().setValidMultiblock(true);
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void invalidateMultiblock() {
		if (!this.getWorld().isRemote) {
			int x = (int) this.getPos().x;
			int y = (int) this.getPos().y;
			int z = (int) this.getPos().z;
			for (int xOff = -1; xOff <= 1; xOff++) {
				for (int zOff = -1; zOff <= 1; zOff++) {
					if (this.getWorld().getBlockTileEntity(x + xOff, y,
					        z + zOff) != null) {
						if (this.getWorld().getBlockTileEntity(x + xOff, y,
						        z + zOff) instanceof TileTimeMachine) {
							TileTimeMachine tile = (TileTimeMachine) this
							        .getWorld().getBlockTileEntity(x + xOff, y,
							                z + zOff);
							if (tile.getPart() != null) {
								if (tile.getPart().getTypeMeta() == TimeMachinePart.TYPE_PANEL)
									((TMPartPanel) tile.getPart())
									        .setBasePosition(0, 0, 0);
								tile.getPart().setValidMultiblock(false);
							}
						}
					}
				}
			}

			validateMultiblock();
		}
	}

	@Override
	public PacketTimeMachineUpdate getPacket() {
		return null;
	}

}
