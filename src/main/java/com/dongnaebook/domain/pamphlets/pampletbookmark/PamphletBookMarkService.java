package com.dongnaebook.domain.pamphlets.pampletbookmark;

import com.dongnaebook.domain.pamphlets.Pamphlet;
import com.dongnaebook.domain.pamphlets.PamphletService;
import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PamphletBookMarkService {

    private final PamphletBookMarkRepository pamphletBookMarkRepository;

    private final UserService userService;
    private final PamphletService pamphletService;

    @Transactional
    public void bookmarkPamphlet(Long userId, Long pamphletId) {
        User user = userService.getUserOrThrow(userId);
        Pamphlet pamphlet = pamphletService.getPamphletOrThrow(pamphletId);

        if (pamphletBookMarkRepository.existsByUserAndPamphlet(user, pamphlet)) {
            throw new IllegalStateException("이미 북마크한 팜플렛입니다.");
        }

        PamphletBookmark bookmark = PamphletBookmark.builder()
                .user(user)
                .pamphlet(pamphlet)
                .build();

        pamphletBookMarkRepository.save(bookmark);
    }

    @Transactional
    public void unbookmarkPamphlet(Long userId, Long pamphletId) {
        User user = userService.getUserOrThrow(userId);
        Pamphlet pamphlet = pamphletService.getPamphletOrThrow(pamphletId);

        pamphletBookMarkRepository.deleteByUserAndPamphlet(user, pamphlet);
    }

    @Transactional(readOnly = true)
    public List<PamphletBookmark> getBookmarkedPamphlets(Long userId) {
        User user = userService.getUserOrThrow(userId);
        return pamphletBookMarkRepository.findByUser(user);
    }
}