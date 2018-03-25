package com.nzarudna.logparser.view;

import java.util.NavigableMap;
import java.util.SortedMap;

public interface StatisticsPrinter {

    void printDurationStatistics(NavigableMap<Double, String> durationStatistics, int topDurationRequestsCount);

    void drawHistogram(SortedMap<Integer, Integer> statisticsData);

    void printProgramExecuteDuration(long duration);

    void printHelp(String helpText);
}
