package com.siteclearing.controller;

import com.siteclearing.exception.SimulatorException;
import com.siteclearing.services.BulldozerService;
import com.siteclearing.services.SiteService;
import com.siteclearing.services.impl.BulldozerServiceImpl;
import com.siteclearing.services.impl.SiteServiceImpl;
import com.siteclearing.strategy.BulldozerStrategy;

/**
 * This class is the Controller of the simulator. It is the entry point, it is here all services and
 * strategies are instanced Only the controller can start a simulation.
 */
public class SimulatorController {

    private final BulldozerStrategy strategy;

    /**
     * Constructor
     */
    public SimulatorController() {
        SiteService siteService = new SiteServiceImpl();
        BulldozerService bulldozerService = new BulldozerServiceImpl(siteService);
        this.strategy = new BulldozerStrategy(siteService, bulldozerService);
    }

    /**
     * Take the site map given in parameter and start the strategy with each command given.
     *
     * @param args user file path of the site map
     * @throws SimulatorException
     */
    public void runSimulator(String... args) throws SimulatorException {

        // Get file path
        String filePath;
        if (args.length > 0 && args[0] != null && !args[0].isEmpty()) {
            filePath = args[0];
        } else {
            throw new SimulatorException("Invalid file path in parameter");
        }

        this.strategy.startStrategy(filePath);
    }
}
