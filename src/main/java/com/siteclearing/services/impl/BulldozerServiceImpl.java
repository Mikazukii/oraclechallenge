package com.siteclearing.services.impl;

import com.siteclearing.constants.MachineDirection;
import com.siteclearing.exception.SimulatorException;
import com.siteclearing.model.Bulldozer;
import com.siteclearing.model.Site;
import com.siteclearing.model.Square;
import com.siteclearing.services.BulldozerService;
import com.siteclearing.services.SiteService;
import com.siteclearing.utils.MessageUtil;

import static com.siteclearing.constants.MachineDirection.*;

public class BulldozerServiceImpl implements BulldozerService {

    private final SiteService siteService;

    /**
     * Constructor
     *
     * @param siteService service to manipulate the site
     */
    public BulldozerServiceImpl(SiteService siteService) {
        this.siteService = siteService;
    }

    @Override
    public boolean advance(Bulldozer bulldozer, Site site, boolean finalDestination) throws SimulatorException {

        boolean success;

        // Add fuel unit
        bulldozer.addFuelUnit(1);

        // Get next position
        int currentX = bulldozer.getX();
        int currentY = bulldozer.getY();

        int nextX = currentX;
        int nextY = currentY;
        switch (bulldozer.getDirection()) {
            case NORTH:
                nextY = currentY - 1;
                break;
            case EAST:
            default:
                nextX = currentX + 1;
                break;
            case SOUTH:
                nextY = currentY + 1;
                break;
            case WEST:
                nextX = currentX - 1;
                break;
        }

        bulldozer.setX(nextX);
        bulldozer.setY(nextY);

        success = this.siteService.checkPositionValidity(site, nextX, nextY);

        // Gets square type
        if (success) {
            Square nextSquare = this.siteService.getSquare(site, nextX, nextY);

            if (!nextSquare.isCleared()) {
                switch (nextSquare.getType()) {
                    case o:
                        break;
                    case r:
                        bulldozer.addFuelUnit(1);
                        break;
                    case t:
                        bulldozer.addFuelUnit(1);
                        if (!finalDestination) {
                            bulldozer.addPaintDamagedUnit(1);
                        }
                        break;
                    case T:
                        bulldozer.addFuelUnit(1);
                        if (!finalDestination) {
                            bulldozer.addPaintDamagedUnit(1);
                        }
                        siteService.addTreeDestructionUnit(site);
                        MessageUtil.displayMessageLn("\n!!! A protected tree has been destructed !!!");
                        success = false;
                        break;
                    default:
                        throw new SimulatorException("Undefined square type");
                }
                this.siteService.clearSquare(site, nextX, nextY);
            }
        }
        return success;
    }

    @Override
    public boolean right(Bulldozer bulldozer) throws SimulatorException {
        bulldozer.setDirection(getNewDirection(bulldozer.getDirection(), false));
        return true;
    }

    @Override
    public boolean left(Bulldozer bulldozer) throws SimulatorException {
        bulldozer.setDirection(getNewDirection(bulldozer.getDirection(), true));
        return true;
    }

    /**
     * Gets the new direction of the bulldozer
     *
     * @param currentDirection the current direction
     * @param isLeft           true if turns left, false if turns right
     * @return false if one problem
     * @throws SimulatorException
     */
    private MachineDirection getNewDirection(MachineDirection currentDirection, boolean isLeft) throws SimulatorException {

        MachineDirection newDirection;
        switch (currentDirection) {
            case EAST:
                if (isLeft) {
                    newDirection = NORTH;
                } else {
                    newDirection = SOUTH;
                }
                break;
            case NORTH:
                if (isLeft) {
                    newDirection = WEST;
                } else {
                    newDirection = EAST;
                }
                break;
            case WEST:
                if (isLeft) {
                    newDirection = SOUTH;
                } else {
                    newDirection = NORTH;
                }
                break;
            case SOUTH:
                if (isLeft) {
                    newDirection = EAST;
                } else {
                    newDirection = WEST;
                }
                break;
            default:
                throw new SimulatorException("Unknown bulldozer direction");
        }
        return newDirection;
    }
}
