package com.nzarudna.logparser.model.parser;

import com.nzarudna.logparser.model.request.Request;
import com.nzarudna.logparser.model.request.UriRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class LogParserTest {

    private LogParser logParser;

    @Before
    public void setUp() {
        logParser = LogParser.getInstance();
    }

    @Test
    public void parseLog_assertUriRequestType() throws LogParserException, ParseException {

        String logLine = "2015-08-19 00:00:02,814 (http--0.0.0.0-28080-245) [CUST:CUS5T27233] /substypechange.do?msisdn=300501633574 in 17";

        Request request = logParser.parseLog(logLine);

        assertTrue(request instanceof UriRequest);
    }

    @Test
    public void parseLog_assertParsedParams() throws LogParserException, ParseException {

        String logLine = "2015-08-19 00:00:02,814 (http--0.0.0.0-28080-245) [CUST:CUS5T27233] /substypechange.do?msisdn=300501633574 in 17";
        Date datetime = new SimpleDateFormat(LogParser.DATE_FORMAT).parse("2015-08-19 00:00:02,814");
        URI uri = URI.create("/substypechange.do?msisdn=300501633574");

        Request request = logParser.parseLog(logLine);

        assertTrue(request instanceof UriRequest);

        UriRequest uriRequest = (UriRequest) request;
        assertEquals(uriRequest.getDatetime(), datetime);
        assertEquals(uriRequest.getThreadName(), "http--0.0.0.0-28080-245");
        assertEquals(uriRequest.getUserContext(), "CUST:CUS5T27233");
        assertEquals(uriRequest.getUri(), uri);
        assertEquals(uriRequest.getDuration(), 17);
    }

    @Test
    public void parseLog_emptyContext_assertParsedParams() throws LogParserException, ParseException {

        String logLine = "2015-08-19 00:00:02,814 (http--0.0.0.0-28080-245) [] /substypechange.do?msisdn=300501633574 in 17";
        Date datetime = new SimpleDateFormat(LogParser.DATE_FORMAT).parse("2015-08-19 00:00:02,814");
        URI uri = URI.create("/substypechange.do?msisdn=300501633574");

        Request request = logParser.parseLog(logLine);

        assertTrue(request instanceof UriRequest);

        UriRequest uriRequest = (UriRequest) request;
        assertEquals(uriRequest.getDatetime(), datetime);
        assertEquals(uriRequest.getThreadName(), "http--0.0.0.0-28080-245");
        assertEquals(uriRequest.getUserContext(), "");
        assertEquals(uriRequest.getUri(), uri);
        assertEquals(uriRequest.getDuration(), 17);
    }
}
