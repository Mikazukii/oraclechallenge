package com.siteclearing.services.impl;

import com.siteclearing.constants.SquareType;
import com.siteclearing.exception.SimulatorException;
import com.siteclearing.model.Site;
import com.siteclearing.services.SiteService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class SiteServiceImplTest {

    private static SiteService siteService;
    private static Site site;

    @BeforeClass
    public static void initSite() throws SimulatorException {
        siteService = new SiteServiceImpl();
        String filePath = "src/test/java/com/siteclearing/siteMapTest.txt";
        site = siteService.constructSiteMap(filePath);
    }

    @Test
    public void constructSiteMap() {

        assertNotNull(site);
        assertEquals(10, site.getLength());
        assertEquals(5, site.getWidth());
        assertEquals(SquareType.o, site.getSquare(0, 0).getType());
        assertEquals(SquareType.r, site.getSquare(0, 2).getType());
        assertEquals(SquareType.t, site.getSquare(5, 4).getType());
        assertEquals(SquareType.T, site.getSquare(7, 1).getType());
    }

    @Test
    public void addTreeDestructionUnit() {

        int nbDestructedTree = siteService.getNbDestructedTree(site);
        siteService.addTreeDestructionUnit(site);
        assertEquals(nbDestructedTree + 1, site.getNbTreeDestructed());
    }

    @Test
    public void displaySiteMap() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream sysOut = System.out;
        System.setOut(new PrintStream(outContent));

        siteService.displaySiteMap(site);
        assertEquals("\n" +
                "o\to\tt\to\to\to\to\to\to\to\t\n" +
                "o\to\to\to\to\to\to\tT\to\to\t\n" +
                "r\tr\tr\to\to\to\to\tT\to\to\t\n" +
                "r\tr\tr\tr\to\to\to\to\to\to\t\n" +
                "r\tr\tr\tr\tr\tt\to\to\to\to\t\n", outContent.toString());
        System.setOut(sysOut);
    }

    @Test
    public void checkPositionValidity() {
        assertTrue(siteService.checkPositionValidity(site, 0, 0));
        assertTrue(siteService.checkPositionValidity(site, 0, 4));
        assertTrue(siteService.checkPositionValidity(site, 9, 4));
        assertTrue(siteService.checkPositionValidity(site, 9, 0));

        assertFalse(siteService.checkPositionValidity(site, -1, 0));
        assertFalse(siteService.checkPositionValidity(site, 0, 5));
        assertFalse(siteService.checkPositionValidity(site, 10, 4));
        assertFalse(siteService.checkPositionValidity(site, 10, 0));
    }

    @Test
    public void clearSquare() {

        int nbUncleared = siteService.getNbUncleared(site);
        assertFalse(siteService.getSquare(site, 0, 0).isCleared());
        siteService.clearSquare(site, 0, 0);
        assertEquals(nbUncleared - 1, siteService.getNbUncleared(site));
        assertTrue(siteService.getSquare(site, 0, 0).isCleared());
    }
}