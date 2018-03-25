package com.nzarudna.logparser.model.request;

import java.util.Date;
import java.util.List;

/**
 * Request to resource
 */
public class ResourceRequest extends Request {

    private String resourceName;
    private List<String> payloadElements;

    public ResourceRequest() {
        super();
    }

    public ResourceRequest(Date datetime, String threadName, String userContext, String resourceName,
                           List<String> payloadElements, int duration) {
        super(datetime, threadName, userContext, duration);
        this.resourceName = resourceName;
        this.payloadElements = payloadElements;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public List<String> getPayloadElements() {
        return payloadElements;
    }

    public void setPayloadElements(List<String> payloadElements) {
        this.payloadElements = payloadElements;
    }

    @Override
    public String getResourceIdentifier() {
        return resourceName;
    }
}
