package org.interview.connect;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TwitterConnector {

    private static final String FILTER_STATUS_URL = "https://stream.twitter.com/1.1/statuses/filter.json";
    private final HttpRequestFactory httpRequestFactory;

    @Autowired
    public TwitterConnector(HttpRequestFactory httpRequestFactory) {
        this.httpRequestFactory = httpRequestFactory;
    }

    /**
     * Method which returns TweetStream with real-time tweets filtered by the passed token
     *
     * @param token token to filter tweets by
     * @return TweetStream of tweets filtered by the token
     * @throws IOException if an error occurred during connection to Twitter
     */
    public TweetStream getTweetStreamFilteredByToken(String token) throws IOException {
        HttpRequest httpRequest = httpRequestFactory.buildGetRequest(new GenericUrl(FILTER_STATUS_URL + "?track=" + token));
        HttpResponse response = httpRequest.execute();
        return new TweetStream(response.getContent());
    }
}

