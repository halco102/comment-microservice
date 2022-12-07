package com.reddit.comment.payload.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    private Long userId;

    private Long postId;

    private String comment;

    private String parentId;


}
