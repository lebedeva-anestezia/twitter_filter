package org.interview.domain;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import org.interview.domain.TweetStream;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


public class TwitterConnector {

    private static final String FILTER_STATUS_URL = "https://stream.twitter.com/1.1/statuses/filter.json";
    private final HttpRequestFactory httpRequestFactory;

    @Autowired
    public TwitterConnector(HttpRequestFactory httpRequestFactory) {
        this.httpRequestFactory = httpRequestFactory;
    }

    public TweetStream getTweetStreamFilteredByToken(String token) throws IOException {
        HttpRequest httpRequest = httpRequestFactory.buildGetRequest(new GenericUrl(FILTER_STATUS_URL + "?track=" + token));
        HttpResponse response = httpRequest.execute();
        return new TweetStream(response.getContent());
    }
}

