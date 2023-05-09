package com.sysmap.parrot.services;

import com.amazonaws.services.pi.model.NotAuthorizedException;
import com.sysmap.parrot.entities.Following;
import com.sysmap.parrot.entities.User;
import com.sysmap.parrot.repository.PostRepository;
import com.sysmap.parrot.entities.Like;
import com.sysmap.parrot.entities.Post;
import com.sysmap.parrot.dto.CreatePostRequest;
import com.sysmap.parrot.services.fileUpload.IFileUploadService;
import com.sysmap.parrot.services.security.IJWTService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class PostService implements IPostService {
    private final PostRepository postRepository;
    private IJWTService _jwtService;
    private IFileUploadService _fileUploadService;
    private UserService _userService;

    @Override
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @Override
    public List<Post> getFriendPosts() {
        String userId = _jwtService.getLoggedUserId();
        Optional<User> optionalUser = _userService.getUserById(userId);
        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("Usuário não encontrado com o ID: " + userId);
        }

        User user = optionalUser.get();
        List<String> followingUserIds = new ArrayList<>();
        for (Following following : user.getFollowing()) {
            followingUserIds.addAll(following.getUsers());
        }

        List<Post> allPosts = postRepository.findAll();
        List<Post> friendPosts = new ArrayList<>();
        for (Post post : allPosts) {
            if (followingUserIds.contains(post.getUserId())) {
                friendPosts.add(post);
            }
        }
        return friendPosts;
    }

    @Override
    public Optional<Post> getPostById(String id){
        return postRepository.findById(id);
    }

    @Override
    public Post createPost(MultipartFile photo, String userId, String title, String description){

        if(!Objects.equals(userId, _jwtService.getLoggedUserId())){
            throw new NotAuthorizedException("NÃO PODE CRIAR POST COM ID DIFERENTE!");
        }else {
            Optional<User> optionalUser = _userService.getUserById(userId);
            User user = optionalUser.get();
            Random random = new Random();
            var randomNumber =   Integer.toString(random.nextInt(1000));
            var randomString ="";
            for (int i = 0; i < 3; i++) {
                char c = (char) (random.nextInt(26) + 'a');
                randomString += c;
            }

            var fileName = "Post/"+ randomNumber + randomString + "." + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")+ 1);

            String photoUri = _fileUploadService.upload(photo, fileName);
            var post = new Post();
            post.setUserId(userId);
            post.setTitle(title);
            post.setDescription(description);
            post.setImage(photoUri);
            post.setCreated(LocalDateTime.now());
            post.setLikes(new ArrayList<>());
            post.setComments(new ArrayList<>());

            return postRepository.save(post);
        }
    }

    @Override
    public Post editPost(String id, CreatePostRequest request) {
        String userId =  _jwtService.getLoggedUserId();
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new NoSuchElementException("Post ou usuário não encontrado ");
        }

        Post post = optionalPost.get();

        if(!Objects.equals(post.getUserId(), userId)) {
            throw new NotAuthorizedException("Você não é o autor do post!");
        }

        if(request.getDescription() != null && !request.getDescription().isEmpty()) post.setDescription(request.getDescription());
        if(request.getTitle() != null && !request.getTitle().isEmpty()) post.setTitle(request.getTitle());
        postRepository.save(post);

        return post;
    }

    @Override
    public String likePost(String id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.get();
        String userId =  _jwtService.getLoggedUserId();
        boolean liked = false;

        for (Like like : post.getLikes()) {
            if (like.getUserId().toString().equals("["+userId+"]")) {
                liked = true;
                post.getLikes().remove(like);
                break;
            }
        }

        if(userId.isBlank()){
            return "Insira um userID";
        }
        else if (!liked) {
            Like like = new Like(Collections.singletonList(userId));
            post.getLikes().add(like);
            postRepository.save(post);
            return "Você deu like no post!";
        } else {
            postRepository.save(post);
            return "Você retirou o like nesse post.";
        }
    }

    @Override
    public String deletePost(String postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new NoSuchElementException("Post não encontrado com esse id: " + postId);
        }
        Post post = optionalPost.get();

        if (!Objects.equals(post.getUserId(), _jwtService.getLoggedUserId())) {
            throw new NotAuthorizedException("Você não é o autor do post!");
        }

        postRepository.deleteById(postId);
        return "Post deletado com sucesso";
    }
}
