package com.dongnaebook.domain.region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="regions")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Region {
    @Id
    @Column(length = 10)
    private String code; // 법정동코드

    @Column(nullable = false)
    private String name; // 법정동명

}
