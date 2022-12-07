package com.reddit.comment.model.comment;

import com.reddit.comment.feign.post.dto.PostDto;
import com.reddit.comment.feign.user.dto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Reply extends BaseComment {

    private String parentId;

    public Reply(String id, PostDto postDto, UserDto userDto, String comment, LocalDateTime createdAt) {
        super(id, postDto, userDto, comment, createdAt);
    }

    public Reply(String id, PostDto postDto, UserDto userDto, String comment, LocalDateTime createdAt, String parentId) {
        super(id, postDto, userDto, comment, createdAt);
        this.parentId = parentId;
    }
}
