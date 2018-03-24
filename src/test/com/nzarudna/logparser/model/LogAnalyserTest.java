
package com.nzarudna.logparser.model;

import com.nzarudna.logparser.model.request.Request;
import com.nzarudna.logparser.model.request.ResourceRequest;
import com.nzarudna.logparser.model.request.UriRequest;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class LogAnalyserTest {

    @Test
    public void getRequestDurationStatistics() throws URISyntaxException, IllegalAccessException, InstantiationException {
        String resourceIdentifier_1 = "res_1";
        String resourceIdentifier_2 = "res_2";

        List<Request> requests = new ArrayList<>();
        requests.add(createResourceRequest(resourceIdentifier_1, 10));
        requests.add(createResourceRequest(resourceIdentifier_2, 30));
        requests.add(createUriRequest(resourceIdentifier_1, 20));

        LogAnalyser logAnalyser = new LogAnalyser(requests);
        NavigableMap<Double, String> actualStatistics = logAnalyser.getRequestDurationStatistics();

        NavigableMap<Double, String> expectedStatistics = new TreeMap<>();
        expectedStatistics.put(30., resourceIdentifier_2);
        expectedStatistics.put(15., resourceIdentifier_1);

        assertEquals(expectedStatistics, actualStatistics);
    }

    @Test
    public void getHourlyRequestNumberStatistics() throws IllegalAccessException, InstantiationException {

        List<Request> requests = new ArrayList<>();
        requests.add(createRequest(UriRequest.class, 9));
        requests.add(createRequest(ResourceRequest.class, 9));
        requests.add(createRequest(UriRequest.class, 5));

        LogAnalyser logAnalyser = new LogAnalyser(requests);
        SortedMap<Integer, Integer> actualStatistics = logAnalyser.getHourlyRequestNumberStatistics();

        SortedMap<Integer, Integer> expectedStatistics = new TreeMap<>();
        expectedStatistics.put(5, 1);
        expectedStatistics.put(9, 2);

        assertEquals(expectedStatistics, actualStatistics);
    }

    private UriRequest createUriRequest(String uri, int duration) throws URISyntaxException, InstantiationException, IllegalAccessException {
        UriRequest uriRequest = TestUtils.mock(UriRequest.class);
        uriRequest.setUri(new URI(uri));
        uriRequest.setDuration(duration);
        return uriRequest;
    }

    private ResourceRequest createResourceRequest(String resourceName, int duration) throws InstantiationException, IllegalAccessException {
        ResourceRequest resourceRequest = TestUtils.mock(ResourceRequest.class);
        resourceRequest.setResourceName(resourceName);
        resourceRequest.setDuration(duration);
        return resourceRequest;
    }

    private <T extends Request> T createRequest(Class<T> requestClass, int hourTime)
            throws InstantiationException, IllegalAccessException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourTime);

        T request = TestUtils.mock(requestClass);
        request.setDatetime(calendar.getTime());
        return request;
    }
}