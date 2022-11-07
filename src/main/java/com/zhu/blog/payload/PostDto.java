package com.zhu.blog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {

    private long id;
    // title should not be null or empty
    // title should have at least 2 characters
    @NotEmpty
    @Size(min=2, message = "Post title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min=10,message = "Description should have 10 characters")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDto> comments;


}
