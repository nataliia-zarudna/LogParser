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

    public HashMap<String, Double> getRequestDurationStatistics() {

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

        HashMap<String, Double> durationStatistics = new HashMap<>();
        for (String resourceIdentifier : requestDurations.keySet()) {

            RequestStatistics requestStatistics = requestDurations.get(resourceIdentifier);
            durationStatistics.put(resourceIdentifier, requestStatistics.getAverage());
        }

        return durationStatistics;
    }

    public SortedMap<Integer, Integer> getHourlyRequestNumberStatistics() {

        SortedMap<Integer, Integer> requestNumberStatistics = new TreeMap<>();

        Calendar calendar = Calendar.getInstance();
        for (Request request : requests) {

            calendar.setTime(request.getDatetime());
            Integer requestHour = calendar.get(Calendar.MINUTE); //TODO: for test. Replace with HOUR_OF_DAY

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
