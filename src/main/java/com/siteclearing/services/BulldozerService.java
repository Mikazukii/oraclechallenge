package com.siteclearing.services;

import com.siteclearing.exception.SimulatorException;
import com.siteclearing.model.Bulldozer;
import com.siteclearing.model.Site;

public interface BulldozerService {

    /**
     * Advance the machine by 1 unit
     *
     * @param bulldozer        bulldozer to move
     * @param site             site where the bulldozer is moving
     * @param finalDestination true if the machine advance to the final destination
     * @return true if no problem
     * @throws SimulatorException
     */
    boolean advance(Bulldozer bulldozer, Site site, boolean finalDestination) throws SimulatorException;

    /**
     * Turn the machine left
     *
     * @param bulldozer to turn
     * @return true if no problem
     * @throws SimulatorException
     */
    boolean left(Bulldozer bulldozer) throws SimulatorException;

    /**
     * Turn the machine right
     *
     * @param bulldozer to turn
     * @return true if no problem
     * @throws SimulatorException
     */
    boolean right(Bulldozer bulldozer) throws SimulatorException;
}
