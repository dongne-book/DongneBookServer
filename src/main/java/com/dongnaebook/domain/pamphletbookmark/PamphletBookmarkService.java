package com.dongnaebook.domain.pamphletbookmark;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.pamphlets.DTO.PamphletResponseDTO;
import com.dongnaebook.domain.pamphlets.PamphletMapper;
import com.dongnaebook.domain.pamphlets.PamphletRepository;
import com.dongnaebook.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                        .user(userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new))
                        .pamphlet(pamphletRepository.findById(pamphletId).orElseThrow(EntityNotFoundException::new))
                        .build();
        pamphletBookMarkRepository.save(pamphletBookMark);
        return true;
    }
    @Transactional
    public Boolean delete(String email, Long pamphletId){
        if(pamphletBookMarkRepository.existsByUser_EmailAndPamphlet_Id(email, pamphletId)){
            PamphletBookmark pamphletBookMark = pamphletBookMarkRepository.findByUser_EmailAndPamphlet_Id(email, pamphletId)
                    .orElseThrow(() -> new NotFoundException("북마크 되지 않은 팜플랫"));
            pamphletBookMarkRepository.delete(pamphletBookMark);
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    public List<PamphletResponseDTO> bookmarkedPamphlets(String email) {
        return pamphletBookMarkRepository.findByUser_Email(email)
                .stream().map(PamphletBookmark::getPamphlet).toList()
                .stream().map(PamphletMapper::toResponseDto).toList();
    }

}
