package com.reddit.comment.model.likedislike;


import com.reddit.comment.feign.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class LikeDislikeComment {

    private String commentId;

    private UserDto userDto;

    private boolean isLike;

}
