package de.mineformers.timetravel.api.energy;

/**
 * TimeTravel
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
