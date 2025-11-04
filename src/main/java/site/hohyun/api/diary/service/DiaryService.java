package site.hohyun.api.diary.service;

import java.util.List;
import site.hohyun.api.diary.domain.DiaryDTO;

public interface DiaryService 
{
    List<DiaryDTO> findAll();
    DiaryDTO findById(Long id);
    DiaryDTO save(DiaryDTO diary);
    DiaryDTO saveAll(List<DiaryDTO> diaries);
    void delete(Long id);
}
