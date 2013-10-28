package de.mineformers.kybology.api.energy;

/**
 * Kybology
 * 
 * IEnergyStorage
 * 
 * @author Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public interface IEnergyStorage {
    
    public int getStoredEnergy();

    public int getMaximumEnergy();

    public boolean addEnergy(int energy);

}