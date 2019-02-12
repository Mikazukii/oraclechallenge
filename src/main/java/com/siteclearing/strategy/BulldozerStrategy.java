package com.siteclearing.strategy;

import com.siteclearing.exception.SimulatorException;
import com.siteclearing.model.Bulldozer;
import com.siteclearing.model.Site;
import com.siteclearing.services.BulldozerService;
import com.siteclearing.utils.CommandHistoryUtil;
import com.siteclearing.utils.MessageUtil;

public class BulldozerStrategy {

    private final BulldozerService bulldozerService;

    public BulldozerStrategy(BulldozerService bulldozerService) {
        this.bulldozerService = bulldozerService;
    }

    /**
     * @param site      Site of the simulation
     * @param bulldozer Bulldozer of the simulation
     * @param command   entered by the user
     * @return true if no problem
     * @throws SimulatorException
     */
    public boolean executeCommand(Site site, Bulldozer bulldozer, String command) throws SimulatorException {

        boolean success;

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
        } else if (command.startsWith("a")) {
            int steps = 1;
            if (command.length() > 1) {
                command = command.replaceAll("[^\\d]", "");
                steps = Integer.parseInt(command);
            }
            CommandHistoryUtil.addCommand("advance " + steps);
            success = advanceStrategy(site, bulldozer, steps);
        } else {
            // Invalid input
            MessageUtil.displayMessageLn("\n!!! Instruction unknown !!!");
            CommandHistoryUtil.addCommand(command);
            success = false;
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
