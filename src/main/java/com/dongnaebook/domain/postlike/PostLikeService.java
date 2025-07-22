package com.dongnaebook.domain.postlike;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.pamphletbookmark.PamphletBookmark;
import com.dongnaebook.domain.post.PostRepository;
import com.dongnaebook.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                        .user(userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new))
                        .post(postRepository.findById(postId).orElseThrow(EntityNotFoundException::new))
                        .build();
        postLikeRepository.save(postLike);
        return true;
    }
    @Transactional
    public Boolean delete(String email, Long postId){
        if(postLikeRepository.existsByUser_EmailAndPost_Id(email, postId)){
            PostLike postLike = postLikeRepository.findByUser_EmailAndPost_Id(email, postId)
                    .orElseThrow(() -> new NotFoundException("북마크 되지 않은 팜플랫"));
            postLikeRepository.delete(postLike);
            return true;
        }else{
            return false;
        }
    }
}
