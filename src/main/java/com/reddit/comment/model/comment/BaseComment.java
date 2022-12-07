package com.reddit.comment.model.comment;

import com.reddit.comment.feign.post.dto.PostDto;
import com.reddit.comment.feign.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseComment {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    private PostDto postDto;

    private UserDto userDto;

    private String comment;

    private LocalDateTime createdAt;

}
