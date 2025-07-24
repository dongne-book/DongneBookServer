package com.dongnaebook.domain.postlike;

import com.dongnaebook.domain.post.Post;
import com.dongnaebook.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post-likes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;
}
