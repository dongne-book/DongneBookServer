package com.dongnaebook.domain.postlike;

import com.dongnaebook.domain.pamphletbookmark.PamphletBookmark;
import com.dongnaebook.domain.pamphlets.Pamphlet;
import com.dongnaebook.domain.post.Post;
import com.dongnaebook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUser_EmailAndPost_Id(String userEmail, Long postId);

    List<PostLike> findByUser_Email(String userEmail);

    boolean existsByUser_EmailAndPost_Id(String userEmail, Long postId);

    void deleteByUserAndPost(User user, Post Post);

    int countByPost_Id(Long postId);

}
