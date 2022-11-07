package com.zhu.blog.service.impl;

import com.zhu.blog.entity.Comment;
import com.zhu.blog.entity.Post;
import com.zhu.blog.exception.BlogAPIException;
import com.zhu.blog.exception.ResourceNotFoundException;
import com.zhu.blog.payload.CommentDto;
import com.zhu.blog.repository.CommentRepository;
import com.zhu.blog.repository.PostRepository;
import com.zhu.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);
        // search post
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post","id",postId));
        // set post to comment entity
        comment.setPost(post);

        // comment entity to DB
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

        // retrieve comments by post id
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {

        // search post entity by post id
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post","id",postId));

        // retrieve comment by comment id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        // search post entity by post id
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post","id",postId));

        // retrieve comment by comment id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        // search post entity by post id
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post","id",postId));

        // retrieve comment by comment id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapToDTO(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        commentDto.setName(comment.getName());
        return commentDto;
    }
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        return comment;
    }

}
