package com.sysmap.parrot.services;

import com.amazonaws.services.pi.model.NotAuthorizedException;
import com.sysmap.parrot.dto.CreateCommentRequest;
import com.sysmap.parrot.entities.Comment;
import com.sysmap.parrot.entities.Like;
import com.sysmap.parrot.entities.User;
import com.sysmap.parrot.repository.PostRepository;
import com.sysmap.parrot.entities.Post;
import com.sysmap.parrot.services.security.IJWTService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class CommentService implements ICommentService {
    private final PostRepository postRepository;
    private IJWTService _jwtService;
    private UserService _userService;
    private PostService _postService;

    @Override
    public List<Comment> getComments(String postId) {
        Optional<Post> optionalPost = _postService.getPostById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            return post.getComments();
        } else throw new NoSuchElementException("Não foi possível encontrar o post com o ID: " + postId);
    }

    @Override
    public Comment getCommentById(String postId, String commentId) {
        Optional<Post> optionalPost = _postService.getPostById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            List<Comment> comments = post.getComments();
            for (Comment comment : comments) {
                if (comment.getId().equals(commentId)) {
                    return comment;
                }
            }
            throw new NoSuchElementException("Não foi possível encontrar o comentário com o ID: " + commentId);
        } else {
            throw new NoSuchElementException("Não foi possível encontrar o post com o ID: " + postId);
        }
    }

    @Override
    public Comment createComment(String postId, CreateCommentRequest request) {
        String userId =  _jwtService.getLoggedUserId();
        Optional<Post> optionalPost = _postService.getPostById(postId);
        Optional<User> optionalUser = _userService.getUserById(userId);

        if (optionalPost.isEmpty() || optionalUser.isEmpty()) {
            throw new NoSuchElementException("Post ou usuário não encontrado ");
        }

        Post post = optionalPost.get();

        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString());
        comment.setUserId(userId);
        comment.setContent(request.getContent());
        comment.setLikes(new ArrayList<>());

        post.getComments().add(comment);
        postRepository.save(post);

        return comment;
    }

    @Override
    public Comment editComment(String postId, String commentId, CreateCommentRequest request) {
        String userId =  _jwtService.getLoggedUserId();

        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new NoSuchElementException("Post não encontrado com o ID: " + postId);
        }
        Post post = optionalPost.get();

        if (!Objects.equals(post.getUserId(), _jwtService.getLoggedUserId())) {
            throw new NotAuthorizedException("Você não é o autor do comentário!");
        }

        List<Comment> comments = post.getComments();
        for (Comment comment : comments) {
            if (comment.getId().equals(commentId)) {
                comment.setContent(request.getContent());
                postRepository.save(post);
                return comment;
            }
        }
        throw new NoSuchElementException("Comentário não encontrado com o ID: " + commentId);
    }

    @Override
    public String deleteComment(String postId, String commentId) {
        Optional<Post> optionalPost = _postService.getPostById(postId);
        if (optionalPost.isEmpty()) {
            throw new NoSuchElementException("Post não encontrado com o ID: " + postId);
        }

        Post post = optionalPost.get();
        Optional<Comment> optionalComment = post.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst();

        if (optionalComment.isEmpty()) {
            throw new NoSuchElementException("Comentário não encontrado com o ID: " + commentId);
        }

        Comment comment = optionalComment.get();

        if (!Objects.equals(comment.getUserId(), _jwtService.getLoggedUserId())) {
            throw new NotAuthorizedException("Você não é o autor do comentário!");
        }

        post.getComments().remove(comment);
        postRepository.save(post);
        return "Comentário deletado!";
    }

    @Override
    public Comment likeComment(String postId, String commentId) {
        String userId = _jwtService.getLoggedUserId();
        Optional<Post> optionalPost = _postService.getPostById(postId);
        boolean liked = false;

        if (optionalPost.isEmpty()) {
            throw new NoSuchElementException("Post não encontrado com o ID: " + postId);
        }

        Post post = optionalPost.get();
        List<Comment> comments = post.getComments();
        for (Comment comment : comments) {
                for (Like like : comment.getLikes()) {
                    if (like.getUserId().toString().equals("["+userId+"]")) {
                        liked = true;
                        comment.getLikes().remove(like);
                        break;
                    }
                }

            if (!liked) {
                //Like no comentario
                Like like = new Like(Collections.singletonList(userId));
                comment.getLikes().add(like);
                postRepository.save(post);

            } else {
                //dislike no comentario
                postRepository.save(post);
            }
            return comment;
        }
        throw new NoSuchElementException("Comentário não encontrado com o ID: " + commentId);
    }

}
