package com.dongnaebook.domain.post.postlike;

import com.dongnaebook.domain.post.Post;
import com.dongnaebook.domain.post.PostService;
import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;

    private final UserService userService;
    private final PostService postService;

    @Transactional
    public void likePost(Long userId, Long pamphletId) {
        User user = userService.getUserOrThrow(userId);
        Post post = postService.getPostOrThrow(pamphletId);

        if (postLikeRepository.existsByUserAndPost(user, post)) {
            throw new IllegalStateException("이미 좋아요한 게시글입니다.");
        }
        PostLike postLike = PostLike.builder()
                .user(user)
                .post(post)
                .build();
        postLikeRepository.save(postLike);
    }

    @Transactional
    public void unlikePost(Long userId, Long postId) {
        User user = userService.getUserOrThrow(userId);
        Post post = postService.getPostOrThrow(postId);

        postLikeRepository.deleteByUserAndPost(user, post);
    }
}
