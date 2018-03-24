package com.nzarudna.logparser.model.request;

import java.net.URI;
import java.util.Date;

public class UriRequest extends Request {

    private URI uri;

    public UriRequest() {
        super();
    }

    public UriRequest(Date datetime, String threadName, String userContext, URI uri, int duration) {
        super(datetime, threadName, userContext, duration);
        this.uri = uri;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    @Override
    public String getResourceIdentifier() {
        return uri.getPath();
    }
}
