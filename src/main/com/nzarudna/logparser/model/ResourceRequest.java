package com.nzarudna.logparser.model;

import java.util.Date;
import java.util.List;

public class ResourceRequest extends Request {

    private String resourceName;
    private List<String> payloadElements;

    public ResourceRequest(Date datetime, String threadName, String userContext, String resourceName,
                           List<String> payloadElements, int duration) {
        super(datetime, threadName, userContext, duration);
        this.resourceName = resourceName;
        this.payloadElements = payloadElements;
    }

    @Override
    public String getResourceIdentifier() {
        return resourceName;
    }
}
