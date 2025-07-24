package com.dongnaebook.domain.postlike;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.post.DTO.PostResponseDTO;
import com.dongnaebook.domain.post.PostMapper;
import com.dongnaebook.domain.post.PostRepository;
import com.dongnaebook.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Boolean create(String email, Long postId){
        if(postLikeRepository.existsByUser_EmailAndPost_Id(email, postId)){
            return false;
        }
        PostLike postLike =
                PostLike.builder()
                        .user(userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다")))
                        .post(postRepository.findById(postId).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다")))
                        .build();
        postLikeRepository.save(postLike);
        return true;
    }
    @Transactional
    public Boolean delete(String email, Long postId){
        Optional<PostLike> existingPostLike = postLikeRepository.findByUser_EmailAndPost_Id(email, postId);
        if(existingPostLike.isPresent()){
            PostLike postLike = existingPostLike.get();
            postLikeRepository.delete(postLike);
            return true;
        }else{
            throw new NotFoundException("Post Not Found");
        }
    }
}
