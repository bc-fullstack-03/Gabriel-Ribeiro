package com.sysmap.parrot.services;

import com.sysmap.parrot.dto.CreateCommentRequest;
import com.sysmap.parrot.entities.Comment;

import java.util.List;

public interface ICommentService {
    List<Comment> getComments(String postId);

    Comment getCommentById(String postId, String commentId);

    Comment createComment(String postId, CreateCommentRequest request);

    Comment editComment(String postId, String commentId, CreateCommentRequest request);

    String deleteComment(String postId, String commentId);

    Comment likeComment(String postId, String commentId);
}
