package com.nzarudna.logparser.model.out;

import java.util.HashMap;
import java.util.SortedMap;

public abstract class StatisticsPrinter {

    public abstract void printDurationStatistics(HashMap<String, Double> durationStatistics);

    public abstract void drawHistogram(SortedMap<Integer, Integer> statisticsData);

}
