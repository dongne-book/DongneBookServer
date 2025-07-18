package com.dongnaebook.domain.pamphlets.pampletbookmark;

import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.pamphlets.Pamphlet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PamphletBookMarkRepository extends JpaRepository<PamphletBookmark, Long> {

    Optional<PamphletBookmark> findByUserAndPamphlet(User user, Pamphlet pamphlet);

    List<PamphletBookmark> findByUser(User user);

    boolean existsByUserAndPamphlet(User user, Pamphlet pamphlet);

    void deleteByUserAndPamphlet(User user, Pamphlet pamphlet);
}