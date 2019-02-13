package com.siteclearing.services.impl;

import com.siteclearing.constants.MachineDirection;
import com.siteclearing.exception.SimulatorException;
import com.siteclearing.model.Bulldozer;
import com.siteclearing.model.Site;
import com.siteclearing.services.BulldozerService;
import com.siteclearing.services.SiteService;
import org.junit.BeforeClass;

import static org.junit.Assert.assertEquals;

public class BulldozerServiceImplTest {

    private static BulldozerService bulldozerService;
    private static Bulldozer bulldozer;
    private static Site site;

    @BeforeClass
    public static void init() throws SimulatorException {
        SiteService siteService = new SiteServiceImpl();
        bulldozerService = new BulldozerServiceImpl(siteService);
        bulldozer = new Bulldozer();
        String filePath = "src/test/java/com/siteclearing/siteMapTest.txt";
        site = siteService.constructSiteMap(filePath);
    }

    @org.junit.Test
    public void advance() throws SimulatorException {

        int x = bulldozer.getX();
        int y = bulldozer.getY();
        int fuelUnitUsed = bulldozer.getFuelUnitUsed();
        int paintDamagedUnit = bulldozer.getPaintDamagedUnit();

        // Test advance 3
        bulldozerService.advance(bulldozer, site, false);
        bulldozerService.advance(bulldozer, site, false);
        bulldozerService.advance(bulldozer, site, true);

        assertEquals(MachineDirection.EAST, bulldozer.getDirection());
        assertEquals(x += 3, bulldozer.getX());
        assertEquals(y, bulldozer.getY());
        assertEquals(fuelUnitUsed += 4, bulldozer.getFuelUnitUsed());
        assertEquals(paintDamagedUnit, bulldozer.getPaintDamagedUnit());

        // Test turn right and advance 2
        bulldozerService.right(bulldozer);
        bulldozerService.advance(bulldozer, site, false);
        bulldozerService.advance(bulldozer, site, false);
        bulldozerService.advance(bulldozer, site, false);
        bulldozerService.advance(bulldozer, site, true);

        assertEquals(MachineDirection.SOUTH, bulldozer.getDirection());
        assertEquals(x, bulldozer.getX());
        assertEquals(y += 4, bulldozer.getY());
        assertEquals(fuelUnitUsed += 7, bulldozer.getFuelUnitUsed());
        assertEquals(paintDamagedUnit, bulldozer.getPaintDamagedUnit());

        // Test turn left and advance 5
        bulldozerService.left(bulldozer);
        bulldozerService.advance(bulldozer, site, false);
        bulldozerService.advance(bulldozer, site, false);
        bulldozerService.advance(bulldozer, site, false);
        bulldozerService.advance(bulldozer, site, false);
        bulldozerService.advance(bulldozer, site, true);

        assertEquals(MachineDirection.EAST, bulldozer.getDirection());
        assertEquals(x += 5, bulldozer.getX());
        assertEquals(y, bulldozer.getY());
        assertEquals(fuelUnitUsed += 8, bulldozer.getFuelUnitUsed());
        assertEquals(paintDamagedUnit += 1, bulldozer.getPaintDamagedUnit());

        // Test turn left and advance 2
        bulldozerService.left(bulldozer);
        bulldozerService.advance(bulldozer, site, false);
        bulldozerService.advance(bulldozer, site, false);

        assertEquals(MachineDirection.NORTH, bulldozer.getDirection());
        assertEquals(x, bulldozer.getX());
        assertEquals(y - 2, bulldozer.getY());
        assertEquals(fuelUnitUsed + 3, bulldozer.getFuelUnitUsed());
        assertEquals(paintDamagedUnit + 1, bulldozer.getPaintDamagedUnit());
    }

    @org.junit.Test
    public void right() throws SimulatorException {

        bulldozer.setDirection(MachineDirection.EAST);
        bulldozerService.right(bulldozer);
        assertEquals(MachineDirection.SOUTH, bulldozer.getDirection());

        bulldozerService.right(bulldozer);
        assertEquals(MachineDirection.WEST, bulldozer.getDirection());

        bulldozerService.right(bulldozer);
        assertEquals(MachineDirection.NORTH, bulldozer.getDirection());

        bulldozerService.right(bulldozer);
        assertEquals(MachineDirection.EAST, bulldozer.getDirection());

    }

    @org.junit.Test
    public void left() throws SimulatorException {

        bulldozer.setDirection(MachineDirection.EAST);
        bulldozerService.left(bulldozer);
        assertEquals(MachineDirection.NORTH, bulldozer.getDirection());

        bulldozerService.left(bulldozer);
        assertEquals(MachineDirection.WEST, bulldozer.getDirection());

        bulldozerService.left(bulldozer);
        assertEquals(MachineDirection.SOUTH, bulldozer.getDirection());

        bulldozerService.left(bulldozer);
        assertEquals(MachineDirection.EAST, bulldozer.getDirection());
    }
}