package com.siteclearing.services.impl;

import com.siteclearing.constants.SquareType;
import com.siteclearing.exception.SimulatorException;
import com.siteclearing.model.Site;
import com.siteclearing.model.Square;
import com.siteclearing.services.SiteService;
import com.siteclearing.utils.MessageUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class SiteServiceImpl implements SiteService {

    @Override
    public Site constructSiteMap(String filePath) throws SimulatorException {

        // Parse file path
        Path path = Paths.get(filePath);
        String[] rows;
        try {
            rows = Files.lines(path).toArray(String[]::new);
        } catch (IOException e) {
            throw new SimulatorException("Impossible to parse the file given");
        }

        // Instance the site
        int nbRows = rows.length;
        int nbColumns = rows[0].length();
        Site site = new Site(nbRows, nbColumns);

        // Fill the site map
        for (int i = 0; i < nbRows; i++) {

            char[] lands = rows[i].toCharArray();
            for (int j = 0; j < lands.length; j++) {

                Square square = new Square();
                String name = String.valueOf(lands[j]);
                SquareType type = SquareType.valueOf(name);
                square.setType(type);
                site.getSquares()[i][j] = square;
                if (SquareType.T != type) {
                    site.addSquareToClear(1);
                }
            }
        }
        return site;
    }

    @Override
    public Square getSquare(Site site, int x, int y) {
        return site.getSquare(x, y);
    }

    @Override
    public void addTreeDestructionUnit(Site site) {
        site.addDestructedTree(1);
    }

    @Override
    public void displaySiteMap(Site site) {

        Arrays.stream(site.getSquares())
                .forEach(
                        row -> {
                            System.out.println();
                            Arrays.stream(row)
                                    .filter(Objects::nonNull)
                                    .map(Square::getType)
                                    .forEach(type -> System.out.print(type + "\t"));
                        });
        System.out.println();
    }

    @Override
    public boolean checkPositionValidity(Site site, int x, int y) {

        if (x < 0 || x >= site.getLength() || y < 0 || y >= site.getWidth()) {
            MessageUtil.displayMessageLn(
                    "\n!!! The machine navigate beyond the boundaries of the site !!!");
            return false;
        }
        return true;
    }

    @Override
    public void clearSquare(Site site, int x, int y) {

        Square square = site.getSquare(x, y);
        square.setCleared(true);
        if (SquareType.T != square.getType()) site.addSquareToClear(-1);
    }

    @Override
    public int getNbUncleared(Site site) {
        return site.getSquaresToClear();
    }

    @Override
    public int getNbDestructedTree(Site site) {
        return site.getNbTreeDestructed();
    }
}
