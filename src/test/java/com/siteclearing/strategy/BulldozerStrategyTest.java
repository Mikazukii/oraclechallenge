package com.siteclearing.strategy;

import com.siteclearing.exception.SimulatorException;
import com.siteclearing.model.Bulldozer;
import com.siteclearing.model.Site;
import com.siteclearing.services.BulldozerService;
import com.siteclearing.services.SiteService;
import com.siteclearing.services.impl.BulldozerServiceImpl;
import com.siteclearing.services.impl.SiteServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BulldozerStrategyTest {

    private static BulldozerStrategy strategy;
    private static Site site;
    private static Bulldozer bulldozer;

    @BeforeClass
    public static void init() throws SimulatorException {
        SiteService siteService = new SiteServiceImpl();
        site = siteService.constructSiteMap("src/test/java/com/siteclearing/siteMapTest.txt");
        BulldozerService bulldozerService = new BulldozerServiceImpl(siteService);
        bulldozer = new Bulldozer();
        strategy = new BulldozerStrategy(siteService, bulldozerService);
    }

    @Test
    public void executeCommand() throws SimulatorException {

        assertTrue(strategy.executeCommand(site, bulldozer, "a 8"));
        strategy.executeCommand(site, bulldozer, "r");
        assertFalse(strategy.executeCommand(site, bulldozer, "a 1"));
        strategy.executeCommand(site, bulldozer, "l");
        assertFalse(strategy.executeCommand(site, bulldozer, "a 3"));
        assertFalse(strategy.executeCommand(site, bulldozer, "q"));
        assertFalse(strategy.executeCommand(site, bulldozer, "*"));
    }
}