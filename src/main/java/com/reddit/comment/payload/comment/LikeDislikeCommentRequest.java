package com.reddit.comment.payload.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeDislikeCommentRequest {

    private String id;

    private boolean isLike;

}
