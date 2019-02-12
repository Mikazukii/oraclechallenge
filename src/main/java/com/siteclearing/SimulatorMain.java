package com.siteclearing;

import com.siteclearing.controller.SimulatorController;
import com.siteclearing.exception.SimulatorException;

public class SimulatorMain {

    public static void main(String[] args) throws SimulatorException {

        // Run simulation
        SimulatorController controller = new SimulatorController();
        controller.runSimulator(args);
    }
}
