package com.nzarudna.logparser.model.out;

import java.util.Collection;
import java.util.HashMap;
import java.util.SortedMap;

public class ConsoleStatisticsPrinter extends StatisticsPrinter {

    private static final int HISTOGRAM_MAX_HEIGHT = 50;
    private static final int HISTOGRAM_STEP_COUNT = 6;

    @Override
    public void printDurationStatistics(HashMap<String, Double> durationStatistics) {

        for (String identifier : durationStatistics.keySet()) {
            System.out.println(identifier + " - " + durationStatistics.get(identifier));
        }
    }

    @Override
    public void drawHistogram(SortedMap<Integer, Integer> statisticsData) {

        Collection<Integer> values = statisticsData.values();
        int maxValue = 0;
        for (Integer value : values) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        int step = calculateStep(HISTOGRAM_MAX_HEIGHT, HISTOGRAM_STEP_COUNT, maxValue);
        int scaledStep = HISTOGRAM_MAX_HEIGHT * step / maxValue;
        int stepCount = 0;
        System.out.print("  ");
        for (int i = 0; i < HISTOGRAM_MAX_HEIGHT + scaledStep; i++) {
            if (stepCount * scaledStep == i) {
                System.out.print(stepCount * step);
                stepCount++;
            } else {
                System.out.print("  ");
            }
        }
        System.out.print('\n');

        for( int i = 0; i < (HISTOGRAM_MAX_HEIGHT + scaledStep) * 2; i++) {
            System.out.print('-');
        }
        System.out.print('\n');

        for (int key : statisticsData.keySet()) {

            System.out.print(key);
            System.out.print(" | ");
            for (int i = 0; i < statisticsData.get(key) * HISTOGRAM_MAX_HEIGHT / maxValue; i++) {
                System.out.print("* ");
            }
            System.out.print('\n');
        }
    }

    private int calculateStep(int histogramMaxHeight, int stepCount, int maxValue) {

        int minStep = maxValue / (stepCount - 1);
        int roundedMinStep = ((int) (minStep / 10)) * 10;
        return roundedMinStep;
    }
}
