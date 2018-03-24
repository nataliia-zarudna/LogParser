package com.nzarudna.logparser.model;

import java.util.*;

/**
 * Class that analyses logs and aggregates statistics data
 */
public class LogAnalyser {

    private List<Request> requests;

    public LogAnalyser(List<Request> requests) {
        this.requests = requests;
    }

    public NavigableMap<Double, String> getRequestDurationStatistics() {

        HashMap<String, RequestStatistics> requestDurations = new HashMap<>();
        for (Request request : requests) {

            String resourceIdentifier = request.getResourceIdentifier();

            RequestStatistics statisticsByIdentifier;
            if (requestDurations.containsKey(resourceIdentifier)) {
                statisticsByIdentifier = requestDurations.get(resourceIdentifier);
            } else {
                statisticsByIdentifier = new RequestStatistics();
                requestDurations.put(resourceIdentifier, statisticsByIdentifier);
            }
            statisticsByIdentifier.count++;
            statisticsByIdentifier.durationSum += request.getDuration();
        }

        TreeMap<Double, String> durationStatistics = new TreeMap<>();
        for (String resourceIdentifier : requestDurations.keySet()) {

            RequestStatistics requestStatistics = requestDurations.get(resourceIdentifier);
            durationStatistics.put(requestStatistics.getAverage(), resourceIdentifier);
        }

        return durationStatistics.descendingMap();
    }

    public SortedMap<Integer, Integer> getHourlyRequestNumberStatistics() {

        SortedMap<Integer, Integer> requestNumberStatistics = new TreeMap<>();

        Calendar calendar = Calendar.getInstance();
        for (Request request : requests) {

            calendar.setTime(request.getDatetime());
            Integer requestHour = calendar.get(Calendar.HOUR_OF_DAY);

            int requestNumber = 0;
            if (requestNumberStatistics.containsKey(requestHour)) {
                requestNumber = requestNumberStatistics.get(requestHour);
            }
            requestNumberStatistics.put(requestHour, ++requestNumber);
        }

        return requestNumberStatistics;
    }

    private class RequestStatistics {
        int durationSum;
        int count;

        double getAverage() {
            return durationSum / count;
        }
    }
}
