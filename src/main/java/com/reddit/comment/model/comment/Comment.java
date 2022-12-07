package com.reddit.comment.model.comment;

import com.reddit.comment.feign.post.dto.PostDto;
import com.reddit.comment.feign.user.dto.UserDto;
import com.reddit.comment.model.likedislike.LikeDislikeComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document
public class Comment extends BaseComment{

    private List<Reply> replies = new ArrayList<>();

    public Comment(String id, PostDto postDto, UserDto userDto, String comment, LocalDateTime createdAt, List<Reply> replies) {
        super(id, postDto, userDto, comment, createdAt);
        this.replies = replies;
    }

}
