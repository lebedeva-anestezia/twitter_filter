package org.interview.configs;

import com.google.api.client.http.HttpRequestFactory;
import org.interview.oauth.twitter.TwitterAuthenticationException;
import org.interview.oauth.twitter.TwitterAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("authorization.properties"),
        @PropertySource("secrets.properties")
})
public class ApplicationConfig {

    @Value("${consumer.key}")
    private String consumerKey;

    @Value("${consumer.secret}")
    private String consumerSecret;

    @Bean
    public HttpRequestFactory httpRequestFactory() throws TwitterAuthenticationException {
        TwitterAuthenticator authenticator = new TwitterAuthenticator(System.out,
                consumerKey, consumerSecret);

        return authenticator.getAuthorizedHttpRequestFactory();
    }
}
