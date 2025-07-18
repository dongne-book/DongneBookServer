package com.dongnaebook.domain.diary;

import com.dongnaebook.domain.album.Album;
import com.dongnaebook.domain.album.AlbumRepository;
import com.dongnaebook.domain.diary.DTO.DiaryRequest;
import com.dongnaebook.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final AlbumRepository albumRepository;

    public List<Diary> getAll() {
        return diaryRepository.findAll();
    }
    public List<Diary> searchByKeyword(String keyword) {
        return diaryRepository.findByKeywordsContaining((keyword));
    }
    public List<Diary> searchByRegion(String region) {
        return diaryRepository.findByRegion(region);
    }
    public Diary createDiary(DiaryRequest request, User user) {
        Diary diary = DiaryMapper.toEntity(request);
        diary.setUser(user);

        Album album = albumRepository.findById(request.getAlbumId())
                .orElseThrow(()-> new IllegalArgumentException("해당 앨범이 없습니다."));
        diary.setAlbum(album);
        diary.setCreatedAt(LocalDateTime.now());
        diary.setUpdatedAt(LocalDateTime.now());
        return diaryRepository.save(diary);
    }
    public Diary updateDiary(Long id, DiaryRequest request, User user) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Diary not found"));
        if(!diary.getUser().getId().equals(user.getId())) {
            throw new SecurityException("No permission to update this diary");
        }
        diary.setTitle(request.getTitle());
        diary.setContent(request.getContent());
        diary.setRegion(request.getRegion());
        diary.setKeywords(request.getKeyword());
        diary.setUpdatedAt(request.getUpdatedAt());
        return diaryRepository.save(diary);

    }
    public void deleteDiary(Long id,  User user) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Diary not found"));
        if(!diary.getUser().getId().equals(user.getId())) {
            throw new SecurityException("No permission to delete this diary");
        }

        diaryRepository.delete(diary);
    }
    public Diary getDiary(Long id) {
        return diaryRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Diary not found"));
    }

}
