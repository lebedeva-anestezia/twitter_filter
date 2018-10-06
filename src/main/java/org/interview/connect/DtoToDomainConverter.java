package org.interview.connect;

import org.interview.domain.model.Tweet;
import org.interview.domain.model.User;
import org.interview.dto.TweetDTO;
import org.interview.dto.UserDTO;

class DtoToDomainConverter {

    static Tweet convertTweetDTO(TweetDTO tweetDTO) {
        Tweet.Builder builder = Tweet.builder();
        builder.setId(tweetDTO.getId());
        builder.setCreatedAt(tweetDTO.getCreatedAt());
        builder.setText(tweetDTO.getText());
        builder.setUser(convertUserDTO(tweetDTO.getUser()));
        return builder.create();
    }

    static User convertUserDTO(UserDTO userDTO) {
        User.Builder builder = User.builder();
        builder.setId(userDTO.getId());
        builder.setCreatedAt(userDTO.getCreatedAt());
        builder.setName(userDTO.getName());
        builder.setScreenName(userDTO.getScreenName());
        return builder.create();
    }
}
