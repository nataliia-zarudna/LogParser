package com.nzarudna.logparser.view;

public class PrinterFactory {

    private static StatisticsPrinter instance;

    public static StatisticsPrinter getInstance() {
        if (instance == null) {
            instance = new ConsoleStatisticsPrinter();
        }
        return instance;
    }
}
