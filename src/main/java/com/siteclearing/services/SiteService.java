package com.siteclearing.services;

import com.siteclearing.exception.SimulatorException;
import com.siteclearing.model.Site;
import com.siteclearing.model.Square;

public interface SiteService {

    /**
     * Constructs a site map thanks to the file path given in parameter
     *
     * @param filePath site map file path
     * @throws SimulatorException
     */
    Site constructSiteMap(String filePath) throws SimulatorException;

    /**
     * Displays the site map built
     *
     * @param site site to display
     */
    void displaySiteMap(Site site);

    /**
     * Checks if the position is available on the site map
     *
     * @param site site to use
     * @param x    x-axis position
     * @param y    y-axis position
     * @return true if no problem
     */
    boolean checkPositionValidity(Site site, int x, int y);

    /**
     * Gets a the square of the position given
     *
     * @param site site to use
     * @param x    x-axis position
     * @param y    y-axis position
     * @return true if no problem
     */
    Square getSquare(Site site, int x, int y);

    /**
     * Add units of protected tree destruction
     *
     * @param site site to use
     */
    void addTreeDestructionUnit(Site site);

    /**
     * Marks the square as cleared
     *
     * @param site site to use
     * @param x    x-axis position
     * @param y    y-axis position
     */
    void clearSquare(Site site, int x, int y);

    /**
     * Gets the number of uncleared squares
     *
     * @param site site to use
     * @return true if no problem
     */
    int getNbUncleared(Site site);

    /**
     * Gets the number of units of protected tree destruction
     *
     * @param site site to use
     * @return true if no problem
     */
    int getNbDestructedTree(Site site);
}
