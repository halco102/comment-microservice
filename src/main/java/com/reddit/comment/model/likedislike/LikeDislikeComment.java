package com.reddit.comment.model.likedislike;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class LikeDislikeComment {

    private String id;

    private Long userId;

    private boolean isLike;

}
