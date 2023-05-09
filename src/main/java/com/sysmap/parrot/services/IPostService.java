package com.sysmap.parrot.services;

import com.sysmap.parrot.dto.CreatePostRequest;
import com.sysmap.parrot.entities.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    List<Post> getAllPosts();

    List<Post> getFriendPosts();

    Optional<Post> getPostById(String id);

    Post createPost(MultipartFile photo, String userId, String title, String description);

    Post editPost(String id, CreatePostRequest request);

    String likePost(String id);

    String deletePost(String postId);
}
