package com.dongnaebook.domain.pamphletbookmark;

import com.dongnaebook.domain.pamphlets.Pamphlet;
import com.dongnaebook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PamphletBookmarkRepository extends JpaRepository<PamphletBookmark, Long> {

    Optional<PamphletBookmark> findByUser_EmailAndPamphlet_Id(String userEmail, Long pamphletId);

    List<PamphletBookmark> findByUser_Email(String userEmail);

    boolean existsByUser_EmailAndPamphlet_Id(String userEmail, Long pamphletId);

    void deleteByUserAndPamphlet(User user, Pamphlet pamphlet);
}