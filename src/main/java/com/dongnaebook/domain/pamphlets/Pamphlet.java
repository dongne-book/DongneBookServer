package com.dongnaebook.domain.pamphlets;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="pamphlets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pamphlet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "region_code", referencedColumnName = "code")
//    private Region region;

    @Column(nullable=false)
    private String title;

    @Column(nullable=true)
    private String content;

    @Column(nullable=false)
    private String version;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
