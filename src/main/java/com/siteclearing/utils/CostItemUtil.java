package com.siteclearing.utils;

import com.siteclearing.model.Bulldozer;
import com.siteclearing.model.Site;

import static com.siteclearing.constants.CostItemRatio.*;

public class CostItemUtil {

    private static final String LINE_COST_FORMAT = "%-30s %10s %10s";
    private static final String TOTAL_FORMAT = "%s %46s";

    /**
     * Displays the total cost of the simulation
     *
     * @param site      Site of the simulation
     * @param bulldozer Bulldozer of the simulation
     */
    public static void displayTotalCost(Site site, Bulldozer bulldozer) {

        MessageUtil.displayMessageLn("\nThe costs for this land clearing operation were:\n");

        System.out.format(LINE_COST_FORMAT, "Item", "Quality", "Cost");
        MessageUtil.displayMessageLn();

        int nbCommUnit = bulldozer.getCommUnit();
        int commCredit = nbCommUnit * COMM_CREDIT_RATIO;
        MessageUtil.displayWithFormat(
                LINE_COST_FORMAT, "communication overhead", nbCommUnit, commCredit);

        int fuelUsedUnit = bulldozer.getFuelUnitUsed();
        int fuelUsedCredit = fuelUsedUnit * FUEL_CREDIT_RADIO;
        MessageUtil.displayWithFormat(LINE_COST_FORMAT, "fuel usage", fuelUsedUnit, fuelUsedCredit);

        int nbUncleared = site.getSquaresToClear();
        int unclearedCredit = nbUncleared * UNCLEARED_CREDIT_RADIO;
        MessageUtil.displayWithFormat(
                LINE_COST_FORMAT, "uncleared squares", nbUncleared, unclearedCredit);

        int nbDestructedTree = site.getNbTreeDestructed();
        int destructionCredit = nbDestructedTree * DESTRUCTION_CREDIT_RATIO;
        MessageUtil.displayWithFormat(
                LINE_COST_FORMAT, "destruction of protected tree", nbDestructedTree, destructionCredit);

        int paintDamagedUnit = bulldozer.getPaintDamagedUnit();
        int paintDamagedCredit = paintDamagedUnit * PAINT_DMG_CREDIT_RATIO;
        MessageUtil.displayWithFormat(
                LINE_COST_FORMAT, "paint damage to bulldozer", paintDamagedUnit, paintDamagedCredit);

        MessageUtil.displayMessageLn("----");
        int totalCredit =
                commCredit + fuelUsedCredit + unclearedCredit + destructionCredit + paintDamagedCredit;

        MessageUtil.displayWithFormat(TOTAL_FORMAT, "Total", totalCredit);

        MessageUtil.displayMessageLn("\nThank you for using the Aconex site clearing simulator.");
    }
}
