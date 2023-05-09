package com.sysmap.parrot.controller;

import com.sysmap.parrot.entities.Post;
import com.sysmap.parrot.dto.CreatePostRequest;
import com.sysmap.parrot.services.IPostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/posts")
@AllArgsConstructor
public class PostController {
    private IPostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> fetchAllPosts(){
        var response = postService.getAllPosts();
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/feed")
    public ResponseEntity<List<Post>> fetchFeed(){
        var response = postService.getFriendPosts();
        return ResponseEntity.status(200).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Post>> fetchPostById(@PathVariable String id){
        var response = postService.getPostById(id);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> createPost(@RequestPart("photo") MultipartFile photo,
                                           @RequestPart("userId") String userId,
                                           @RequestPart("title") String title,
                                           @RequestPart("description") String description){
        Post response = postService.createPost(photo, userId, title, description);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable String id){
       var response = postService.likePost(id);
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> editPost(@PathVariable String id, @RequestBody CreatePostRequest request){
        Post response = postService.editPost(id, request);
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> createUser(@PathVariable String id){
        var response = postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
