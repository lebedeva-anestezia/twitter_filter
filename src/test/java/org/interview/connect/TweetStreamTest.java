package org.interview.connect;

import org.interview.domain.model.Tweet;
import org.junit.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TweetStreamTest {

    private String tweetJSON = "{\"created_at\":\"Wed Oct 03 17:58:43 +0000 2018\",\"id\":1047546502418194432,\"text\":\"TWEET\",\"source\":\"\\u003ca href=\\\"http:\\/\\/twitter.com\\/download\\/android\\\" rel=\\\"nofollow\\\"\\u003eTwitter for Android\\u003c\\/a\\u003e\",\"user\":{\"id\":769631495065600000,\"name\":\"Anderson Santos\",\"screen_name\":\"Andersin545\",\"created_at\":\"Sat Aug 27 20:23:42 +0000 2016\"}}";

    @Test
    public void testTweetStreamIsNextTweet() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);

        PrintWriter printWriter = new PrintWriter(out);
        printWriter.print("tweet");
        printWriter.flush();

        TweetStream tweetStream = new TweetStream(in);

        assertTrue(tweetStream.isNextTweet());
    }

    @Test
    public void testTweetStreamNotIsNextTweet() throws IOException {
        PipedInputStream in = new PipedInputStream();

        TweetStream tweetStream = new TweetStream(in);

        assertFalse(tweetStream.isNextTweet());
    }

    @Test
    public void testNextTweet() throws IOException, ParseException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);

        PrintWriter printWriter = new PrintWriter(out);
        printWriter.println(tweetJSON);
        printWriter.flush();

        TweetStream tweetStream = new TweetStream(in);

        Tweet nextTweet = tweetStream.getNextTweet();

        assertNotNull(nextTweet);
        assertEquals(1047546502418194432L, nextTweet.getId().longValue());
        assertEquals(new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH).parse("Wed Oct 03 17:58:43 +0000 2018"),
                nextTweet.getCreatedAt());
        assertEquals("TWEET", nextTweet.getText());
        assertEquals(769631495065600000L, nextTweet.getUser().getId().longValue());
        assertEquals("Anderson Santos", nextTweet.getUser().getName());
        assertEquals("Andersin545", nextTweet.getUser().getScreenName());
        assertEquals(new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH).parse("Sat Aug 27 20:23:42 +0000 2016"),
                nextTweet.getUser().getCreatedAt());
    }

}