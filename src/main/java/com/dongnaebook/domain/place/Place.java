package com.dongnaebook.domain.place;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name="places")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR")
    private String name;

    @Column(columnDefinition = "VARCHAR")
    private String address;

    @Column(nullable = false, columnDefinition = "geometry(Point,4326)")
    private Point location; // 위도, 경도 포함

}
