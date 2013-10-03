package de.mineformers.timetravel.api.energy;

import java.util.HashMap;

import net.minecraft.util.StatCollector;

/**
 * TimeTravel
 * 
 * CrystalType
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class CrystalType {

    public enum Function {
        STORAGE, TRANSFER, NOTHING
    }

    public enum Quality {
        PURE, PURIFIED
    }

    public enum Color {
        BLUE, RED, GOLD
    }

    private Function function;
    private Quality quality;
    private Color color;
    private HashMap<String, Object> data;

    public CrystalType(Function function, Quality quality, Color color) {
        this.function = function;
        this.quality = quality;
        this.color = color;
        data = new HashMap<String, Object>();
    }

    public CrystalType(int function, int quality, int color) {
        this.function = Function.values()[function];
        this.quality = Quality.values()[quality];
        this.color = Color.values()[color];
        data = new HashMap<String, Object>();
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void addData(String key, Object value) {
        data.put(key, value);
    }

    public Object getData(String key) {
        return data.get(key);
    }

    public String getDisplayString() {
        String format = StatCollector
                .translateToLocal("item.timetravel:crystal.format");
        String quality = this.getTranslatedQuality();
        String crystalName = StatCollector
                .translateToLocal("item.timetravel:crystal.name");

        String name = format.replace("%q%", quality);
        name = name.replace("%h%", "Horallium");
        name = name.replace("%n%", crystalName);

        return name;
    }

    public String getTranslatedFunction() {
        return StatCollector.translateToLocal("item.timetravel:crystal.type."
                + this.function.toString().toLowerCase());
    }

    public String getTranslatedQuality() {
        return StatCollector
                .translateToLocal("item.timetravel:crystal.quality."
                        + this.quality.toString().toLowerCase());
    }

    public String getTranslatedColor() {
        return StatCollector.translateToLocal("item.timetravel:crystal.color."
                + this.color.toString().toLowerCase());
    }

}
