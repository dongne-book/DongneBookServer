package com.dongnaebook.domain.diary_region;

import com.dongnaebook.domain.diary.Diary;
import com.dongnaebook.domain.post.Post;
import com.dongnaebook.domain.region.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="diary_regions")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
}
