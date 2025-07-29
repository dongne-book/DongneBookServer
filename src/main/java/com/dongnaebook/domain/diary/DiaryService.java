package com.dongnaebook.domain.diary;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.diary.DTO.DiaryRequestDTO;
import com.dongnaebook.domain.diary.DTO.DiaryResponseDTO;
import com.dongnaebook.domain.diary_post.DiaryPostRepository;
import com.dongnaebook.domain.diary_region.DiaryRegion;
import com.dongnaebook.domain.diary_region.DiaryRegionRepository;
import com.dongnaebook.domain.post.Post;
import com.dongnaebook.domain.post.PostRepository;
import com.dongnaebook.domain.diary.DTO.DiaryDetailDTO;
import com.dongnaebook.domain.diary_post.DiaryPost;
import com.dongnaebook.domain.region.Region;
import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final DiaryPostRepository diaryPostRepository;
    private final DiaryRegionRepository diaryRegionRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public DiaryResponseDTO create(DiaryRequestDTO request) {

        Diary diary = DiaryMapper.toEntity(request);
        diaryRepository.save(diary);

        List<Long> postIds = request.getPostIds();
        if (postIds != null) {
            for (Long postId : postIds) {
                Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new NotFoundException("Post not found with id: " + postId));
                DiaryPost dp = DiaryPost.builder()
                        .diary(diary)
                        .post(post)
                        .build();
                DiaryRegion dr = DiaryRegion.builder()
                        .diary(diary)
                        .region(post.getPlace().getRegion())
                        .build();
                diaryPostRepository.save(dp);
                diaryRegionRepository.save(dr);
            }
        }
        return DiaryMapper.toResponseDTO(diary);
    }


    @Transactional(readOnly = true)
    public DiaryDetailDTO getById(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diary not found"));

        return buildDiaryDetailDTO(diary);
    }


    @Transactional(readOnly = true)
    public List<DiaryResponseDTO> getAll() {
        return diaryRepository.findAll().stream()
                .map(DiaryMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DiaryResponseDTO update(Long id, DiaryRequestDTO dto) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diary not found"));
        diary.update(dto.getTitle(), dto.getContent());
        return DiaryMapper.toResponseDTO(diaryRepository.save(diary));
    }

    public void deleteById(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diary not found"));
        diaryRepository.delete(diary);
    }

    public Page<DiaryDetailDTO> getAllWithPagination(Pageable pageable) {
        Page<Diary> diaryPage = diaryRepository.findAll(pageable);
        return diaryPage.map(this::buildDiaryDetailDTO);
    }

    private DiaryDetailDTO buildDiaryDetailDTO(Diary diary) {
        List<DiaryPost> diaryPosts = diaryPostRepository.findByDiaryId(diary.getId());
        List<Long> postIds = diaryPosts.stream()
                .map(dp -> dp.getPost().getId())
                .collect(Collectors.toList());

        List<Post> posts = postRepository.findByIdIn(postIds);
        User createdBy = userRepository.findByEmail(diary.getCreatedBy())
                .orElseThrow(() -> new NotFoundException("Created user not found"));
        User modifiedBy = userRepository.findByEmail(diary.getModifiedBy())
                .orElseThrow(() -> new NotFoundException("Modified user not found"));
        List<Region> regions = diaryRegionRepository.findByDiaryId(diary.getId()).stream()
                .map(DiaryRegion::getRegion)
                .toList();

        return DiaryMapper.toDetailResponse(diary, createdBy, modifiedBy, regions, posts);
    }

    public Page<DiaryDetailDTO> searchByTitleAndContent(String keyword, Pageable pageable) {
        Page<Diary> diaryPage = diaryRepository.findByTitleOrContentContaining(keyword, keyword, pageable);
        return diaryPage.map(this::buildDiaryDetailDTO);
    }

}
