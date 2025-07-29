package com.dongnaebook.domain.pamphletbookmark;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.pamphlets.DTO.PamphletResponseDTO;
import com.dongnaebook.domain.pamphlets.PamphletMapper;
import com.dongnaebook.domain.pamphlets.PamphletRepository;
import com.dongnaebook.domain.user.UserRepository;
import com.dongnaebook.domain.user.vo.Email;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PamphletBookmarkService {
    private final PamphletBookmarkRepository pamphletBookMarkRepository;
    private final UserRepository userRepository;
    private final PamphletRepository pamphletRepository;

    @Transactional
    public Boolean create(String email, Long pamphletId){
        if(pamphletBookMarkRepository.existsByUser_EmailAndPamphlet_Id(email, pamphletId)){
            return false;
        }
        PamphletBookmark pamphletBookMark =
                PamphletBookmark.builder()
                        .user(userRepository.findByEmail(new Email(email)).orElseThrow(EntityNotFoundException::new))
                        .pamphlet(pamphletRepository.findById(pamphletId).orElseThrow(EntityNotFoundException::new))
                        .build();
        pamphletBookMarkRepository.save(pamphletBookMark);
        return true;
    }
    @Transactional
    public Boolean delete(String email, Long pamphletId){
        Optional<PamphletBookmark> existPamphletBookmark = pamphletBookMarkRepository.findByUser_EmailAndPamphlet_Id(email, pamphletId);
        if(existPamphletBookmark.isPresent()){
            PamphletBookmark pamphletBookMark = existPamphletBookmark.get();
            pamphletBookMarkRepository.delete(pamphletBookMark);
            return true;
        }else{
            throw new NotFoundException("사용자 " + email + "과 팜플릿 ID " + pamphletId + "에 대한 북마크를 찾을 수 없습니다");
        }
    }

    @Transactional
    public List<PamphletResponseDTO> bookmarkedPamphlets(String email) {
        return pamphletBookMarkRepository.findByUser_Email(email)
                .stream().map(PamphletBookmark::getPamphlet).toList()
                .stream().map(PamphletMapper::toResponseDto).toList();
    }

}
