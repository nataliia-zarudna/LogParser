package com.nzarudna.logparser.model.parser;

import com.nzarudna.logparser.model.request.Request;
import com.nzarudna.logparser.model.request.ResourceRequest;
import com.nzarudna.logparser.model.request.UriRequest;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses logs to Request objects
 */
public class LogParser {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS";

    private static final String DATETIME_PATTERN = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}";
    private static final String THREAD_PATTERN = "\\([^\\)]*\\)";
    private static final String USER_CONTEXT_PATTERN = "\\[[^\\]]*\\]";
    private static final String REQUEST_IDENTIFIER_PATTERN = "\\].*in";
    private static final String DURATION_PATTERN = "\\d+$";

    private static LogParser instance;

    public static LogParser getInstance() {
        if (instance == null) {
            instance = new LogParser();
        }
        return instance;
    }

    private LogParser() {
    }

    /**
     * Parse log line to Request object
     * @param logLine string that includes log about one request
     * @return parsed request
     * @throws LogParserException if string has unexpected data
     */
    public Request parseLog(String logLine) throws LogParserException {

        Date datetime = getRequestDatetime(logLine);

        String threadNameStr = getSubstring(logLine, THREAD_PATTERN);
        String threadName = threadNameStr.substring(1, threadNameStr.length() - 1);

        String userContextStr = getSubstring(logLine, USER_CONTEXT_PATTERN);
        String userContext = userContextStr.substring(1, userContextStr.length() - 1);

        String durationStr = getSubstring(logLine, DURATION_PATTERN);
        int duration = Integer.parseInt(durationStr);

        String requestIdentifierStr = getSubstring(logLine, REQUEST_IDENTIFIER_PATTERN);
        String requestIdentifier = requestIdentifierStr.substring(2, requestIdentifierStr.length() - " in".length());

        if (requestIdentifier.charAt(0) == '/') {
            URI uri = URI.create(requestIdentifier);
            return new UriRequest(datetime, threadName, userContext, uri, duration);
        } else {

            String[] identifierTokens = requestIdentifier.split("\\s+");
            String resourceName = identifierTokens[0];

            List<String> payloadElements = new ArrayList<>();
            for (int i = 1; i < identifierTokens.length; i++) {
                payloadElements.add(identifierTokens[i]);
            }
            return new ResourceRequest(datetime, threadName, userContext, resourceName, payloadElements, duration);
        }
    }

    private Date getRequestDatetime(String logLine) throws LogParserException {

        String datetimeStr = getSubstring(logLine, DATETIME_PATTERN);

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dateFormat.parse(datetimeStr);
        } catch (ParseException e) {
            throw new LogParserException("Error during parsing datetime string '" + datetimeStr + "'", e);
        }
    }

    private String getSubstring(String subject, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(subject);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }
}
