package de.mineformers.kybology.timetravel.travelling.timemachine;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import de.mineformers.kybology.core.util.LangHelper;
import de.mineformers.kybology.core.util.NetworkHelper;
import de.mineformers.kybology.core.util.Vector3;
import de.mineformers.kybology.timetravel.entity.PlayerPropertiesTT;
import de.mineformers.kybology.timetravel.lib.Sounds;
import de.mineformers.kybology.timetravel.network.packet.PacketTMPanelUpdate;
import de.mineformers.kybology.timetravel.network.packet.PacketTimeMachineUpdate;
import de.mineformers.kybology.timetravel.tileentity.TileTimeMachine;
import de.mineformers.kybology.timetravel.world.TeleporterTime;

/**
 * Kybology
 * 
 * TMPartPanel
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TMPartPanel extends TimeMachinePart {

    private boolean searchedForModules;
    private ArrayList<TMPartModule> modules;
    private Vector3 basePosition;
    private boolean doCountdown;
    private int countdown;
    private int targetDim;

    public TMPartPanel(TileEntity parent) {
        super(TimeMachinePart.TYPE_PANEL, parent);
        this.setBasePosition(0, 0, 0);
        this.doCountdown = false;
        this.countdown = 10;
        this.targetDim = 0;
        this.modules = new ArrayList<TMPartModule>();
    }

    public Vector3 getBasePosition() {
        return basePosition;
    }

    public void setBasePosition(int x, int y, int z) {
        this.basePosition = new Vector3(x, y, z);
    }

    @Override
    public void setValidMultiblock(boolean valid) {
        super.setValidMultiblock(valid);
        if (!valid) {
            modules.clear();
            searchedForModules = false;
        }
    }

    @Override
    public void initFromTile(TileEntity parent) {
        super.initFromTile(parent);
    }

    public boolean getModuleConflicts() {
        int spaceAvail = 0;
        int timeAvail = 0;
        int playerAvail = 0;
        int itemAvail = 0;
        int powerAvail = 0;

        for (TMPartModule module : modules) {
            switch (module.getType()) {
                case POWER:
                    powerAvail++;
                    break;
                case SPACE:
                    spaceAvail++;
                    break;
                case TIME:
                    timeAvail++;
                    break;
                case ITEM:
                    itemAvail++;
                    break;
                case PLAYER:
                    playerAvail++;
                    break;
                default:
                    break;
            }
        }

        return powerAvail > 1 || timeAvail > 1 || spaceAvail > 1
                || itemAvail > 1 || playerAvail > 1
                || (spaceAvail > 0 && timeAvail > 0);
    }

    public void listModules() {
        for (TMPartModule module : modules) {
            System.out.println(module.getType());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateTick() {
        super.updateTick();
        findModules();
        if (this.isValidMultiblock()) {
            if (doCountdown) {
                if (countdown == 0) {
                    NetworkHelper.sendTilePacket(this.getWorld(),
                            (int) basePosition.x, (int) basePosition.y,
                            (int) basePosition.z);
                }
                if (this.getTick() == 20) {
                    int minX = (int) (basePosition.x - 4);
                    int maxX = (int) (basePosition.x + 4);
                    int minY = (int) basePosition.y;
                    int maxY = (int) (basePosition.y + 2);
                    int minZ = (int) (basePosition.z - 4);
                    int maxZ = (int) (basePosition.z + 4);
                    List<EntityPlayer> players = this.getWorld()
                            .getEntitiesWithinAABB(
                                    EntityPlayer.class,
                                    AxisAlignedBB.getBoundingBox(minX, minY,
                                            minZ, maxX, maxY, maxZ));
                    if (countdown > 0) {
                        this.getWorld().playSoundEffect(basePosition.x,
                                basePosition.y, basePosition.z,
                                Sounds.countdown[countdown - 1], 1, 1);
                    }
                    for (EntityPlayer player : players
                            .toArray(new EntityPlayer[players.size()])) {
                        if (countdown == 0)
                            player.addChatMessage(LangHelper.getString(
                                    "message", "go"));
                        else if (countdown > 0) {
                            player.addChatMessage(countdown + "...");
                        }
                    }
                }

                if (countdown < -1) {
                    doCountdown = false;
                    countdown = 10;
                    double minX = basePosition.x - 0.5D;
                    double maxX = basePosition.x + 1.5D;
                    double minY = basePosition.y;
                    double maxY = basePosition.y + 2;
                    double minZ = basePosition.z - 0.5D;
                    double maxZ = basePosition.z + 1.5D;
                    List<EntityPlayer> players = this.getWorld()
                            .getEntitiesWithinAABB(
                                    EntityPlayer.class,
                                    AxisAlignedBB.getBoundingBox(minX, minY,
                                            minZ, maxX, maxY, maxZ));
                    for (EntityPlayer player : players
                            .toArray(new EntityPlayer[players.size()])) {
                        teleport(player);
                    }
                    NetworkHelper.sendTilePacket(this.getWorld(),
                            (int) basePosition.x, (int) basePosition.y,
                            (int) basePosition.z);
                    return;
                }

                if (this.getTick() == 20)
                    countdown--;
            }
        }

        if (this.getTick() >= 20)
            this.resetTick();
    }

    private void teleport(EntityPlayer player) {

        PlayerPropertiesTT props = PlayerPropertiesTT.getByEntity(player);

        props.setTimeStarted(System.currentTimeMillis());
        props.setSecondsAvail(10);
        props.setSecondsLeft(10);
        props.setTmData(this.getWorld().provider.dimensionId,
                (int) basePosition.x, (int) basePosition.y,
                (int) basePosition.z);

        if ((player.ridingEntity == null) && (player.riddenByEntity == null)
                && ((player instanceof EntityPlayerMP))) {
            EntityPlayerMP thePlayer = (EntityPlayerMP) player;
            if (thePlayer.timeUntilPortal > 0) {
                thePlayer.timeUntilPortal = 10;
            } else {
                thePlayer.timeUntilPortal = 10;
                thePlayer.mcServer.getConfigurationManager()
                        .transferPlayerToDimension(
                                thePlayer,
                                targetDim,
                                new TeleporterTime(thePlayer.mcServer
                                        .worldServerForDimension(targetDim)));
            }
        }
    }

    private void findModules() {
        if (!searchedForModules) {
            for (int xOff = -1; xOff <= 1; xOff++) {
                for (int zOff = -1; zOff <= 1; zOff++) {
                    if (this.getWorld() != null && this.basePosition != null) {
                        if (this.getWorld().getBlockTileEntity(
                                (int) basePosition.x + xOff,
                                (int) basePosition.y + 2,
                                (int) basePosition.z + zOff) != null) {
                            if (this.getWorld().getBlockTileEntity(
                                    (int) basePosition.x + xOff,
                                    (int) basePosition.y + 2,
                                    (int) basePosition.z + zOff) instanceof TileTimeMachine) {
                                TileTimeMachine tile = (TileTimeMachine) this
                                        .getWorld().getBlockTileEntity(
                                                (int) basePosition.x + xOff,
                                                (int) basePosition.y + 2,
                                                (int) basePosition.z + zOff);
                                if (tile.getTypeMeta() == TimeMachinePart.TYPE_MODULE) {
                                    modules.add((TMPartModule) tile.getPart());
                                }
                            }
                        }
                    }
                }
            }
            searchedForModules = true;
        }
    }

    public void startCountdown() {
        this.doCountdown = true;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public void setCountingDown(boolean doCountdown) {
        this.doCountdown = doCountdown;
    }

    public boolean isCountingDown() {
        return doCountdown;
    }

    public void setTargetDimension(int dimId) {
        this.targetDim = dimId;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setIntArray("BasePosition", new int[] { (int) basePosition.x,
                (int) basePosition.y, (int) basePosition.z });

    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        int[] basePos = nbt.getIntArray("BasePosition");
        this.setBasePosition(basePos[0], basePos[1], basePos[2]);
    }

    @Override
    public PacketTimeMachineUpdate getPacket() {
        int baseX = (basePosition != null) ? (int) basePosition.x : 0;
        int baseY = (basePosition != null) ? (int) basePosition.y : 0;
        int baseZ = (basePosition != null) ? (int) basePosition.z : 0;

        return new PacketTMPanelUpdate((int) this.getPos().x,
                (int) this.getPos().y, (int) this.getPos().z,
                ForgeDirection.SOUTH, (byte) 0, null, this.isValidMultiblock(),
                this.getTypeMeta(), baseX, baseY, baseZ, countdown, doCountdown);
    }

}
