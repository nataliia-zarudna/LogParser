package com.nzarudna.logparser.model.out;

import java.util.NavigableMap;
import java.util.SortedMap;

public abstract class StatisticsPrinter {

    public abstract void printDurationStatistics(NavigableMap<Double, String> durationStatistics, int topDurationRequestsCount);

    public abstract void drawHistogram(SortedMap<Integer, Integer> statisticsData);

}
