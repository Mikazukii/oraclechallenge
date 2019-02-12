package com.siteclearing.utils;

import com.siteclearing.exception.SimulatorException;
import com.siteclearing.model.Bulldozer;
import com.siteclearing.model.Site;
import com.siteclearing.services.SiteService;
import com.siteclearing.services.impl.SiteServiceImpl;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class CostItemUtilTest {

    @Test
    public void displayTotalCost() throws SimulatorException {

        SiteService siteService = new SiteServiceImpl();
        String filePath = "src/test/java/com/siteclearing/siteMapTest.txt";
        Site site = siteService.constructSiteMap(filePath);
        site.addSquareToClear(-18);
        site.addDestructedTree(5);

        Bulldozer bulldozer = new Bulldozer();
        bulldozer.addFuelUnit(100);
        bulldozer.addCommUnit(50);
        bulldozer.addPaintDamagedUnit(10);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream sysOut = System.out;
        System.setOut(new PrintStream(outContent));
        CostItemUtil.displayTotalCost(site, bulldozer);
        assertEquals("\n" +
                "The costs for this land clearing operation were:\n" +
                "\n" +
                "Item                              Quality       Cost\n" +
                "communication overhead                 50         50\n" +
                "fuel usage                            100        100\n" +
                "uncleared squares                      30         90\n" +
                "destruction of protected tree           5         50\n" +
                "paint damage to bulldozer              10         20\n" +
                "----\n" +
                "Total                                            310\n" +
                "\n" +
                "Thank you for using the Aconex site clearing simulator.\n", outContent.toString());
        System.setOut(sysOut);
    }
}