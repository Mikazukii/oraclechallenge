package com.siteclearing.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandHistoryUtil {

    private static final List<String> commandList = new ArrayList<>();

    public static void addCommand(String command) {
        commandList.add(command);
    }

    public static void displayHistory() {
        Optional<String> commands = commandList.stream().reduce((i, j) -> String.join(" , ", i, j));
        commands.ifPresent(MessageUtil::displayMessageLn);
    }
}
