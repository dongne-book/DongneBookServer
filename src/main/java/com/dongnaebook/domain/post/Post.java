package com.dongnaebook.domain.post;

import com.dongnaebook.common.domain.BaseEntity;
import com.dongnaebook.domain.album.Album;
import com.dongnaebook.domain.album.DTO.AlbumRequestDTO;
import com.dongnaebook.domain.place.Place;
import com.dongnaebook.domain.post.DTO.PostRequestDTO;
import com.dongnaebook.domain.post_image.PostImage;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDate visitDate;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> images = new ArrayList<>();

    public void update(PostRequestDTO updateDTO) {
        this.content = updateDTO.getContent();
        this.visitDate = updateDTO.getVisitDate();
        this.isPublic = updateDTO.getIsPublic() != null ? updateDTO.getIsPublic() : true;
    }

    public void updatePlace(Place place) {
        this.place = place;
    }

    public void updateAlbum(Album album) {
        this.album = album;
    }

}