package com.dongnaebook.domain.post.postlike;

import com.dongnaebook.domain.pamphlets.Pamphlet;
import com.dongnaebook.domain.post.Post;
import com.dongnaebook.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "post-likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

//    @Column(name = "marked_at", nullable = false, updatable = false)
//    private LocalDateTime markedAt;
//
//    @PrePersist
//    protected void onCreate() {
//        this.markedAt = LocalDateTime.now();
//    }
}
