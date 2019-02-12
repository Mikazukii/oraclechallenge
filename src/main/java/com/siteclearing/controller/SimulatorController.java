package com.siteclearing.controller;

import com.siteclearing.exception.SimulatorException;
import com.siteclearing.model.Bulldozer;
import com.siteclearing.model.Site;
import com.siteclearing.services.BulldozerService;
import com.siteclearing.services.SiteService;
import com.siteclearing.services.impl.BulldozerServiceImpl;
import com.siteclearing.services.impl.SiteServiceImpl;
import com.siteclearing.strategy.BulldozerStrategy;
import com.siteclearing.utils.CommandHistoryUtil;
import com.siteclearing.utils.CostItemUtil;
import com.siteclearing.utils.MessageUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is the Controller of the simulator. It is the entry point, it is here all services and
 * strategies are instanced Only the controller can start a simulation.
 */
public class SimulatorController {

    private final SiteService siteService;
    private final BulldozerStrategy strategy;

    /**
     * Constructor
     */
    public SimulatorController() {
        this.siteService = new SiteServiceImpl();
        BulldozerService bulldozerService = new BulldozerServiceImpl(siteService);
        this.strategy = new BulldozerStrategy(bulldozerService);
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

        MessageUtil.displayMessageLn(
                "\nWelcome to the Aconex site clearing simulator. This is a map of the site:");

        // Load site map from the file given
        Site site = siteService.constructSiteMap(filePath);
        siteService.displaySiteMap(site);

        MessageUtil.displayMessageLn(
                "\nThe bulldozer is currently located at the Northern edge of the site, immediately to the West of the site, and facing East.\n");

        // Read user instructions
        Bulldozer bulldozer = new Bulldozer();
        while (true) {
            MessageUtil.displayMessage("(l)eft, (r)ight, (a)dvance <n>, (q)uit: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String command;
            try {
                command = br.readLine();
            } catch (IOException e) {
                throw new SimulatorException("Impossible to read instructions");
            }

            // Execute command depending to the strategy
            if (!strategy.executeCommand(site, bulldozer, command)) {
                break;
            }
        }

        // Display simulation infos
        MessageUtil.displayMessageLn(
                "\nThe simulation has ended at your request. These are the commands you issued:\n");
        CommandHistoryUtil.displayHistory();
        CostItemUtil.displayTotalCost(site, bulldozer);
    }
}
