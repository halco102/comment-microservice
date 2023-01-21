package com.reddit.comment.model.comment;

import com.reddit.comment.feign.post.dto.PostDto;
import com.reddit.comment.feign.user.dto.UserDto;
import com.reddit.comment.model.likedislike.LikeDislikeComment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class Reply extends BaseComment {

    private String parentId;

    public Reply(String id, PostDto postDto, UserDto userDto, String comment, LocalDateTime createdAt, List<LikeDislikeComment> likeDislikeComments) {
        super(id, postDto, userDto, comment, createdAt, likeDislikeComments);
    }

    public Reply(String id, PostDto postDto, UserDto userDto, String comment, LocalDateTime createdAt, List<LikeDislikeComment> likeDislikeComments, String parentId) {
        super(id, postDto, userDto, comment, createdAt, likeDislikeComments);
        this.parentId = parentId;
    }
}
