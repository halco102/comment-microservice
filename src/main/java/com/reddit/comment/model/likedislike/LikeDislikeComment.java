package com.reddit.comment.model.likedislike;


import com.reddit.comment.feign.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeDislikeComment {

    private String commentId;

    private UserDto userDto;

    private boolean isLike;

}
