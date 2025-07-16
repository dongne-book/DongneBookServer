package com.dongnaebook.domain.diary;

import com.dongnaebook.domain.diary.DTO.DiaryRequest;
import com.dongnaebook.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryService {
    public List<Diary> searchByKeyword(String keyword) {

    }
    public List<Diary> searchByRegion(String region) {

    }
    public Diary createDiary(DiaryRequest request, User user) {

    }
    public Diary updateDiary(Long id, DiaryRequest request, User user) {

    }
    public void deleteDiary(Long id,  User user) {

    }
    public Diary getDiary(Long id) {

    }

}
