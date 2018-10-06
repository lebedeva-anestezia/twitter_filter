package org.interview.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.domain.model.Tweet;
import org.interview.dto.TweetDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TweetStream {

    private final BufferedReader bufferedReader;
    private final ObjectMapper objectMapper;

    public TweetStream(InputStream inputStream) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        objectMapper = new ObjectMapper();
    }

    public Tweet getNextTweet() throws IOException {
        String line = bufferedReader.readLine();
        TweetDTO tweetDTO = objectMapper.readValue(line, TweetDTO.class);

        return new Tweet();
    }

    public boolean isNextTweet() throws IOException {
        return bufferedReader.ready();
    }
}

