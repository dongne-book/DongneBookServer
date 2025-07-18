package com.dongnaebook.domain.pamphlets.pampletbookmark;

import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.pamphlets.Pamphlet;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pamphlet_bookmarks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PamphletBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pamphlet_id")
    private Pamphlet pamphlet;

    @Column(name = "marked_at", nullable = false, updatable = false)
    private LocalDateTime markedAt;

    @PrePersist
    protected void onCreate() {
        this.markedAt = LocalDateTime.now();
    }
}