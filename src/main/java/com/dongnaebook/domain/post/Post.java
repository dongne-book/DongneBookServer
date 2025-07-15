package com.dongnaebook.domain.post;

import com.dongnaebook.common.domain.BaseEntity;
import com.dongnaebook.domain.album.DTO.AlbumRequestDTO;
import com.dongnaebook.domain.place.Place;
import com.dongnaebook.domain.post.DTO.PostRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="posts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDate visitDate;

    private String imageUrl;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    public void update(PostRequestDTO updateDTO) {
        this.content = updateDTO.getContent();
        this.visitDate = updateDTO.getVisitDate();
        this.imageUrl = updateDTO.getImageUrl();
        this.isPublic = updateDTO.getIsPublic() != null ? updateDTO.getIsPublic() : true;
    }

    public void updatePlace(Place place) {
        this.place = place;
    }

}