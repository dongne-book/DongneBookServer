package com.dongnaebook.domain.diary;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.diary.DTO.DiaryRequestDTO;
import com.dongnaebook.domain.diary.DTO.DiaryResponseDTO;
import com.dongnaebook.domain.diary_post.DiaryPostRepository;
import com.dongnaebook.domain.post.DTO.PostResponseDTO;
import com.dongnaebook.domain.post.PostRepository;
import com.dongnaebook.domain.post.PostService;
import com.dongnaebook.domain.diary.DiaryMapper;
import com.dongnaebook.domain.diary.DTO.DiaryDetailDTO;
import com.dongnaebook.domain.diary_post.DiaryPost;
import com.dongnaebook.domain.user.DTO.UserResponseDTO;
import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final DiaryPostRepository diaryPostRepository;
    private final PostService postService;
    private final PostRepository postRepository;

    @Transactional
    public DiaryResponseDTO create(DiaryRequestDTO request) {
        String title = request.getTitle();
        String content = request.getContent();
        List<Long> postIds = request.getPostIds();

        //자동생성은 일단 킵

        //수동생성
        Diary diary = DiaryMapper.toEntity(request, title, content);
        diaryRepository.save(diary);

        // 수동 생성도 postIds 있으면 DiaryPost 저장 가능
        if (postIds != null) {
            for (Long postId : postIds) {
                DiaryPost dp = DiaryPost.builder()
                        .diary(diary)
                        .post(postService.getPostEntityById(postId))
                        .build();
                diaryPostRepository.save(dp);
            }
        }
        return DiaryMapper.toResponse(diary);
    }
    @Transactional(readOnly = true)
    public DiaryDetailDTO getById(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diary not found"));

        // 연관된 Post 리스트 가져오기
        List<DiaryPost> diaryPosts = diaryPostRepository.findByDiaryId(id);
        List<Long> postIds = diaryPosts.stream()
                .map(dp -> dp.getPost().getId())
                .collect(Collectors.toList());

        List<PostResponseDTO> posts = postService.getPostByIds(postIds);

        return DiaryMapper.toDetailResponse(diary, posts);
    }

    @Transactional(readOnly = true)
    public List<DiaryResponseDTO> getAll() {
        return diaryRepository.findAll().stream()
                .map(DiaryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public DiaryResponseDTO update(Long id, DiaryRequestDTO dto) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diary not found"));
        diary.update(dto.getTitle(), dto.getContent());
        return DiaryMapper.toResponse(diaryRepository.save(diary));
    }

    public void deleteById(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diary not found"));
        diaryRepository.delete(diary);
    }
//
//    public List<Diary> getAll() {
//        return diaryRepository.findAll();
//    }
//    public List<Diary> searchByKeyword(String keyword) {
//        return diaryRepository.findByKeywordsContaining((keyword));
//    }
//    public List<Diary> searchByRegion(String region) {
//        return diaryRepository.findByRegion(region);
//    }
//    public DiaryResponseDTO create(DiaryRequestDTO request) {
//        Diary diary = DiaryMapper.toEntity(request);
//        Diary saved = diaryRepository.save(diary);
//        return DiaryMapper.toResponse(saved);
//    }
//    public Diary updateDiary(Long id, DiaryRequestDTO request, User user) {
//        Diary diary = diaryRepository.findById(id)
//                .orElseThrow(()-> new IllegalArgumentException("Diary not found"));
//        if(!diary.getUser().getId().equals(user.getId())) {
//            throw new SecurityException("No permission to update this diary");
//        }
//        diary.setTitle(request.getTitle());
//        diary.setContent(request.getContent());
//        diary.setRegion(request.getRegion());
//        diary.setKeywords(request.getKeyword());
//        diary.setUpdatedAt(request.getUpdatedAt());
//        return diaryRepository.save(diary);
//
//    }
//    public void deleteDiary(Long id, String userEmail) {
//        Diary diary = diaryRepository.findById(id)
//                .orElseThrow(()-> new IllegalArgumentException("Diary not found"));
//        if(!diary.getCreatedBy().equals(userEmail)) {
//            throw new SecurityException("No permission to delete this diary");
//        }
//
//        diaryRepository.delete(diary);
//    }
//    public Diary getDiary(Long id) {
//        return diaryRepository.findById(id)
//                .orElseThrow(()-> new IllegalArgumentException("Diary not found"));
//    }

}
