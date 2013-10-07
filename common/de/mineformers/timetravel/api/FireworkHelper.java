package de.mineformers.timetravel.api;

import java.util.ArrayList;

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

/**
 * TimeTravel
 * 
 * FireworkHelper
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class FireworkHelper {

    public enum FireworkType {
        SMALL_BALL(0), LARGE_BALL(1), STAR(2), CREEPER(3), BURST(4);

        private byte id;

        private FireworkType(int id) {
            this.id = (byte) id;
        }

        public byte getId() {
            return id;
        }

        public static FireworkType getById(byte id) {
            for (int i = 0; i < values().length; i++) {
                if (values()[i].getId() == id) {
                    return values()[i];
                }
            }
            return null;
        }
    }

    public static class FireworkExplosion {

        private boolean trail;
        private FireworkType type;
        private ArrayList<Integer> colors;
        private boolean flicker;
        private ArrayList<Integer> fadeColors;

        public FireworkExplosion(boolean trail, FireworkType type,
                ArrayList<Integer> colors, boolean flicker,
                ArrayList<Integer> fadeColors) {
            setTrail(trail);
            setType(type);
            setColors(colors);
            setFadeColors(fadeColors);
            setFlicker(flicker);
        }

        public FireworkExplosion(boolean trail, FireworkType type, int color,
                boolean flicker) {
            setTrail(trail);
            setType(type);
            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add(color);
            setColors(colors);
            setFadeColors(colors);
            setFlicker(flicker);
        }

        public FireworkExplosion(NBTTagCompound tag) {
            if (tag.getByte("Trail") == 1)
                setTrail(true);
            else
                setTrail(false);
            if (tag.getByte("Flicker") == 1)
                setFlicker(true);
            else
                setFlicker(false);
            setType(FireworkType.getById(tag.getByte("Type")));
            setColors(tag.getIntArray("Colors"));
            setFadeColors(tag.getIntArray("FadeColors"));
        }

        private void setFadeColors(int[] intArray) {
            fadeColors = new ArrayList<Integer>();
            for (int i = 0; i < intArray.length; i++) {
                fadeColors.add(intArray[i]);
            }
        }

        private void setColors(int[] intArray) {
            colors = new ArrayList<Integer>();
            for (int i = 0; i < intArray.length; i++) {
                colors.add(intArray[i]);
            }
        }

        public boolean isTrail() {
            return trail;
        }

        public void setTrail(boolean trail) {
            this.trail = trail;
        }

        public FireworkType getType() {
            return type;
        }

        public void setType(FireworkType type) {
            this.type = type;
        }

        public int[] getColors() {
            int[] array = new int[colors.size()];
            for (int i = 0; i < colors.size(); i++) {
                array[i] = colors.get(i);
            }
            return array;
        }

        public void setColors(ArrayList<Integer> colors) {
            this.colors = colors;
        }

        public void addColor(int color) {
            this.colors.add(color);
        }

        public boolean isFlicker() {
            return flicker;
        }

        public void setFlicker(boolean flicker) {
            this.flicker = flicker;
        }

        public int[] getFadeColors() {
            int[] array = new int[fadeColors.size()];
            for (int i = 0; i < fadeColors.size(); i++) {
                array[i] = fadeColors.get(i);
            }
            return array;
        }

        public void setFadeColors(ArrayList<Integer> fadeColors) {
            this.fadeColors = fadeColors;
        }

        public NBTTagCompound getTag() {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("Trail", getTrail());
            tag.setByte("Type", getType().getId());
            tag.setIntArray("Colors", getColors());
            tag.setByte("Flicker", getFlicker());
            tag.setIntArray("FadeColors", getFadeColors());
            return tag;
        }

        private byte getFlicker() {
            if (isFlicker())
                return 1;
            return 0;
        }

        private byte getTrail() {
            if (isTrail())
                return 1;
            return 0;
        }

    }

    public static class Firework {

        /**
         * Checks if item is applicable.
         * 
         * @param item
         *            the item to check
         * @return true, if is applicable
         */
        public static boolean isApplicable(ItemStack item) {
            switch (item.itemID) {
                case 401:
                    return true;
                default:
                    return false;
            }
        }

        /**
         * Sets explosions.
         * 
         * @param itemStack
         *            item
         * @param exps
         *            explosions
         * @return item stack
         */
        public static ItemStack setExplosions(ItemStack itemStack,
                FireworkExplosion... exps) {
            if (!isApplicable(itemStack))
                return null;
            NBTTagCompound tag = itemStack.getTagCompound();
            if (tag == null) {
                tag = new NBTTagCompound();
                tag.setCompoundTag("Fireworks", new NBTTagCompound());
                itemStack.setTagCompound(tag);
            }
            tag = itemStack.getTagCompound().getCompoundTag("Fireworks");
            NBTTagList list = new NBTTagList();
            for (FireworkExplosion l : exps) {
                list.appendTag(l.getTag());
            }
            tag.setTag("Explosions", list);
            itemStack.getTagCompound().setCompoundTag("Fireworks", tag);
            return itemStack;
        }

        /**
         * Adds explosion.
         * 
         * @param itemStack
         *            item
         * @param explosion
         *            explosion
         * @return item stack
         */
        public static ItemStack addExplosion(ItemStack itemStack,
                FireworkExplosion explosion) {
            if (!isApplicable(itemStack))
                return null;
            NBTTagCompound tag = itemStack.getTagCompound();
            if (tag == null) {
                tag = new NBTTagCompound();
                tag.setCompoundTag("Fireworks", new NBTTagCompound());
                tag.getCompoundTag("Fireworks").setTag("Explosions",
                        new NBTTagList());
                itemStack.setTagCompound(tag);
            }

            tag = itemStack.getTagCompound().getCompoundTag("Fireworks");
            NBTTagList list = tag.getTagList("Explosions");
            list.appendTag(explosion.getTag());
            tag.setTag("Explosions", list);
            itemStack.getTagCompound().setCompoundTag("Fireworks", tag);
            return itemStack;
        }

        /**
         * Gets explosions.
         * 
         * @param itemStack
         *            item
         * @return explosions
         */
        public static FireworkExplosion[] getExplosions(ItemStack itemStack) {
            if (!isApplicable(itemStack))
                return null;
            NBTTagCompound tag = itemStack.getTagCompound();
            if (tag == null) {
                tag = new NBTTagCompound();
                tag.setCompoundTag("Fireworks", new NBTTagCompound());
                tag.getCompoundTag("Fireworks").setTag("Explosions",
                        new NBTTagList());
                itemStack.setTagCompound(tag);
            }
            tag = itemStack.getTagCompound();
            NBTTagList list = tag.getCompoundTag("Fireworks").getTagList(
                    "Explosions");
            FireworkExplosion[] exps = new FireworkExplosion[list.tagCount()];
            for (int i = 0; i < list.tagCount(); i++)
                exps[i] = new FireworkExplosion((NBTTagCompound) list.tagAt(i));
            return exps;
        }

        /**
         * Sets height of flight.
         * 
         * @param itemStack
         *            item
         * @param flight
         *            flight
         * @return item
         */
        public static ItemStack setFlight(ItemStack itemStack, int flight) {
            if (!isApplicable(itemStack))
                return null;
            NBTTagCompound tag = itemStack.getTagCompound();
            if (tag == null) {
                tag = new NBTTagCompound();
                tag.setCompoundTag("Fireworks", new NBTTagCompound());
                itemStack.setTagCompound(tag);
            }

            tag = itemStack.getTagCompound().getCompoundTag("Fireworks");
            tag.setByte("Flight", (byte) flight);
            itemStack.getTagCompound().setCompoundTag("Fireworks", tag);
            return itemStack;
        }

        /**
         * Gets the height.
         * 
         * @param itemStack
         *            item
         * @return height
         */
        public static int getFlight(ItemStack itemStack) {
            if (!isApplicable(itemStack))
                return -1;
            NBTTagCompound tag = itemStack.getTagCompound();
            if (tag == null) {
                tag = new NBTTagCompound();
                tag.setCompoundTag("Fireworks", new NBTTagCompound());
                itemStack.setTagCompound(tag);
                return -1;
            }

            tag = itemStack.getTagCompound().getCompoundTag("Fireworks");
            return tag.getByte("Flight");
        }
    }

    public static void spawnInWorld(World world, float x, float y, float z,
            FireworkExplosion explosion, int flight) {
        ItemStack stack = new ItemStack(401, 1, 0);

        stack = Firework.setExplosions(stack, explosion);
        stack.getTagCompound().getCompoundTag("Fireworks")
                .setByte("Flight", (byte) flight);

        EntityFireworkRocket rocket = new EntityFireworkRocket(world, x, y, z,
                stack);
        world.spawnEntityInWorld(rocket);
    }

}
