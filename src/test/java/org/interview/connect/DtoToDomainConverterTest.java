package org.interview.connect;

import org.interview.domain.model.Tweet;
import org.interview.domain.model.User;
import org.interview.dto.TweetDTO;
import org.interview.dto.UserDTO;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DtoToDomainConverterTest {

    @Test
    public void convertTweetDTO() throws ParseException {
        TweetDTO tweetDTO = new TweetDTO();
        tweetDTO.setId(1L);
        tweetDTO.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-05"));
        tweetDTO.setText("text");
        tweetDTO.setUser(new UserDTO());

        Tweet tweet = DtoToDomainConverter.convertTweetDTO(tweetDTO);

        assertNotNull(tweet);
        assertEquals(tweetDTO.getId(), tweet.getId());
        assertEquals(tweetDTO.getCreatedAt(), tweet.getCreatedAt());
        assertEquals(tweetDTO.getText(), tweet.getText());
        assertNotNull(tweet.getUser());
    }

    @Test
    public void convertUserDTO() throws ParseException {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-05"));
        userDTO.setName("user");
        userDTO.setScreenName("screenUser");

        User user = DtoToDomainConverter.convertUserDTO(userDTO);

        assertNotNull(user);
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getCreatedAt(), user.getCreatedAt());
        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getScreenName(), user.getScreenName());
    }

}