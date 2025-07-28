package com.dongnaebook.domain.post_image;

import com.dongnaebook.domain.post.Post;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name="post-images")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public void update(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
