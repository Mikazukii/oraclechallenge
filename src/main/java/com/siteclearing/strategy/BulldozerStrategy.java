package com.siteclearing.strategy;

import com.siteclearing.exception.SimulatorException;
import com.siteclearing.model.Bulldozer;
import com.siteclearing.model.Site;
import com.siteclearing.services.BulldozerService;
import com.siteclearing.services.SiteService;
import com.siteclearing.utils.CommandHistoryUtil;
import com.siteclearing.utils.CostItemUtil;
import com.siteclearing.utils.MessageUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BulldozerStrategy {

    private final SiteService siteService;
    private final BulldozerService bulldozerService;

    /**
     * Constructor
     *
     * @param siteService      service to manipulate the site model
     * @param bulldozerService service to manipulate th bulldozer model
     */
    public BulldozerStrategy(SiteService siteService, BulldozerService bulldozerService) {
        this.bulldozerService = bulldozerService;
        this.siteService = siteService;
    }

    /**
     * Take the site map given in parameter and start the strategy with each command given.
     *
     * @param filePath
     * @throws SimulatorException
     */
    public void startStrategy(String filePath) throws SimulatorException {

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
            if (!executeCommand(site, bulldozer, command)) {
                break;
            }
        }

        // Display simulation infos
        MessageUtil.displayMessageLn(
                "\nThe simulation has ended at your request. These are the commands you issued:\n");
        CommandHistoryUtil.displayHistory();
        CostItemUtil.displayTotalCost(site, bulldozer);
    }


    /**
     * @param site      Site of the simulation
     * @param bulldozer Bulldozer of the simulation
     * @param command   entered by the user
     * @return true if no problem
     * @throws SimulatorException
     */
    boolean executeCommand(Site site, Bulldozer bulldozer, String command) throws SimulatorException {

        boolean success = false;

        /*
        "r" for right
        "l" for left
        "a <n>" for advance to n square
        "q" for quit
         */
        command = command.replaceAll("\\s", "");
        if (command.matches("r")) {
            CommandHistoryUtil.addCommand("turn right ");
            success = rightStrategy(bulldozer);
        } else if (command.matches("l")) {
            CommandHistoryUtil.addCommand("turn left ");
            success = leftStrategy(bulldozer);
        } else if (command.matches("q")) {
            CommandHistoryUtil.addCommand("quit ");
            success = quitStrategy();
        } else if (command.startsWith("a") && command.length() > 1) {
            command = command.replaceAll("[^\\d]", "");
            if (StringUtils.isNumeric(command)) {
                CommandHistoryUtil.addCommand("advance " + Integer.parseInt(command));
                success = advanceStrategy(site, bulldozer, Integer.parseInt(command));
            } else {
                // Invalid input
                MessageUtil.displayMessageLn("\n!!! Bad Instruction : you must put a number after 'a' !!!");
            }
        } else {
            // Invalid input
            MessageUtil.displayMessageLn("\n!!! Instruction unknown !!!");
            CommandHistoryUtil.addCommand(command);
        }
        return success;
    }

    /**
     * Advance strategy behavior
     *
     * @param site      site of the simulation
     * @param bulldozer bulldozer of the simulation
     * @param nbSquares number of square to advance
     * @return false if one problem
     * @throws SimulatorException
     */
    private boolean advanceStrategy(Site site, Bulldozer bulldozer, int nbSquares) throws SimulatorException {

        boolean success = true;

        bulldozer.addCommUnit(1);
        for (int i = 0; i < nbSquares; i++) {
            if (!bulldozerService.advance(bulldozer, site, nbSquares == i + 1)) {
                success = false;
                break;
            }
        }
        return success;
    }

    /**
     * Right strategy behavior
     *
     * @param bulldozer bulldozer of the simulation
     * @return false if one problem
     * @throws SimulatorException
     */
    private boolean rightStrategy(Bulldozer bulldozer) throws SimulatorException {
        bulldozer.addCommUnit(1);
        return bulldozerService.right(bulldozer);
    }

    /**
     * Left strategy behavior
     *
     * @param bulldozer bulldozer of the simulation
     * @return false if one problem
     * @throws SimulatorException
     */
    private boolean leftStrategy(Bulldozer bulldozer) throws SimulatorException {
        bulldozer.addCommUnit(1);
        return bulldozerService.left(bulldozer);
    }

    /**
     * Left strategy behavior
     *
     * @return false if one problem
     */
    private boolean quitStrategy() {
        return false;
    }
}
