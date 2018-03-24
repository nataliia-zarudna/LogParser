package com.nzarudna.logparser.model.out;

public class PrinterFactory {

    private static StatisticsPrinter instance;

    public static StatisticsPrinter getInstance() {
        if (instance == null) {
            instance = new ConsoleStatisticsPrinter();
        }
        return instance;
    }
}
