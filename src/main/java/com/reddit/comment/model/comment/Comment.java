package com.reddit.comment.model.comment;

import com.reddit.comment.feign.post.dto.PostDto;
import com.reddit.comment.feign.user.dto.UserDto;
import com.reddit.comment.model.likedislike.LikeDislikeComment;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
* Comment is composed of BaseComment and Reply which is also composed of BaseComment with extra value parentId
* the parentId indicates on which comment is the reply connected
* */
@Data
@NoArgsConstructor
@Document
public class Comment extends BaseComment{

    private List<Reply> replies = new ArrayList<>();

    public Comment(String id, PostDto postDto, UserDto userDto, String comment, LocalDateTime createdAt, List<LikeDislikeComment> likeDislikeComment, List<Reply> replies) {
        super(id, postDto, userDto, comment, createdAt, likeDislikeComment);
        this.replies = replies;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "replies=" + replies +
                '}';
    }
}
