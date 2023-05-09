package com.sysmap.parrot.controller;

import com.sysmap.parrot.dto.CreateCommentRequest;
import com.sysmap.parrot.entities.Comment;
import com.sysmap.parrot.services.ICommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/{postId}/comments")
@AllArgsConstructor
public class CommentController {
    private ICommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> fetchComments(@PathVariable String postId){
        var response = commentService.getComments(postId);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable String postId, @PathVariable String commentId){
        var response = commentService.getCommentById(postId, commentId);
        return ResponseEntity.status(200).body(response);
    }
    @PostMapping
    public ResponseEntity<Comment> newComment(@PathVariable String postId, @RequestBody CreateCommentRequest request){
        var response = commentService.createComment(postId, request);
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/{commentId}/like")
    public ResponseEntity<Comment> likeComment(@PathVariable String postId, @PathVariable String commentId){
        var response = commentService.likeComment(postId,commentId);
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> editComment(@PathVariable String postId, @PathVariable String commentId, @RequestBody CreateCommentRequest request){
        var response = commentService.editComment(postId, commentId, request);
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable String postId, @PathVariable String commentId){
        var response = commentService.deleteComment(postId,commentId);
        return ResponseEntity.status(200).body(response);
    }

}
