package com.nzarudna.logparser.view;

import java.util.Collection;
import java.util.NavigableMap;
import java.util.SortedMap;

public class ConsoleStatisticsPrinter implements StatisticsPrinter {

    private static final int HISTOGRAM_MAX_HEIGHT = 20;
    private static final int HISTOGRAM_STEP_COUNT = 6;

    /**
     * Prints first n elements of statistics data
     * @param durationStatistics statistics data
     * @param showLinesCount print elements count
     */
    @Override
    public void printDurationStatistics(NavigableMap<Double, String> durationStatistics, int showLinesCount) {

        System.out.println(showLinesCount + " requests with highest average duration:");

        int i = 0;
        for (Double duration : durationStatistics.keySet()) {
            System.out.println(durationStatistics.get(duration) + " - " + duration);

            if (++i == showLinesCount) {
                break;
            }
        }

        System.out.println();
    }

    /**
     * Draws histogram hour -> request numbers
     * @param statisticsData statistics to display
     */
    @Override
    public void drawHistogram(SortedMap<Integer, Integer> statisticsData) {

        System.out.println("Histogram of hourly number of requests (number/hour):");

        Collection<Integer> values = statisticsData.values();
        int maxValue = 0;
        for (Integer value : values) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        printDurationScale(maxValue);

        for (int hour = 0; hour < 24; hour++) {
            System.out.print(hour + "h | ");

            if (statisticsData.containsKey(hour)) {
                int requestCount = statisticsData.get(hour);
                for (int i = 0; i < requestCount * HISTOGRAM_MAX_HEIGHT / maxValue; i++) {
                    System.out.print("* ");
                }
            }
            System.out.print('\n');
        }
    }

    /**
     * Prints program execute duration in ms
     * @param duration
     */
    @Override
    public void printProgramExecuteDuration(long duration) {
        System.out.println();
        System.out.printf("Program run for %d milliseconds", duration);
    }

    /**
     * Prints program help text
     * @param helpText text to print
     */
    @Override
    public void printHelp(String helpText) {
        System.out.println(helpText);
    }

    private void printDurationScale(int maxValue) {
        int step = calculateStep(HISTOGRAM_STEP_COUNT, maxValue);
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

        for (int i = 0; i < (HISTOGRAM_MAX_HEIGHT + scaledStep) * 2; i++) {
            System.out.print('-');
        }
        System.out.print('\n');
    }

    private int calculateStep(int stepCount, int maxValue) {

        int minStep = maxValue / (stepCount - 1);
        return  ((int) (minStep / 10)) * 10;
    }
}
