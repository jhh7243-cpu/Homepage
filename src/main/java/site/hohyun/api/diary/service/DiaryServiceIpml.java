package site.hohyun.api.diary.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import site.hohyun.api.diary.domain.DiaryDTO;
import site.hohyun.api.diary.domain.DiaryEntity;
import site.hohyun.api.diary.repository.DiaryRepository;

@Service
@RequiredArgsConstructor
public class DiaryServiceIpml implements DiaryService
{
    private final DiaryRepository diaryRepository;

    @Override
    public List<DiaryDTO> findAll() 
    {
        return diaryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DiaryDTO findById(Long id) 
    {
        return diaryRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public DiaryDTO save(DiaryDTO diary) 
    {
        DiaryEntity entity = convertToEntity(diary);
        DiaryEntity savedEntity = diaryRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    @Override
    public DiaryDTO saveAll(List<DiaryDTO> diaries) 
    {
        // List의 첫 번째 요소를 저장 (나중에 전체 저장 로직 추가 가능)
        if (diaries == null || diaries.isEmpty()) 
        {
            return null;
        }
        return save(diaries.get(0));
    }

    @Override
    public void delete(Long id) 
    {
        diaryRepository.deleteById(id);
    }

    /**
     * Entity를 DTO로 변환
     */
    private DiaryDTO convertToDTO(DiaryEntity entity) 
    {
        DiaryDTO dto = new DiaryDTO();
        dto.setId(entity.getDiaryId());
        dto.setYear(String.valueOf(entity.getYear()));
        dto.setMonth(String.valueOf(entity.getMonth()));
        dto.setDay(String.valueOf(entity.getDay()));
        dto.setWeekday(entity.getWeekday());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        return dto;
    }

    /**
     * DTO를 Entity로 변환
     */
    private DiaryEntity convertToEntity(DiaryDTO dto) 
    {
        DiaryEntity entity = new DiaryEntity();
        entity.setDiaryId(dto.getId());
        entity.setYear(dto.getYear() != null && !dto.getYear().isEmpty() ? Integer.parseInt(dto.getYear()) : null);
        entity.setMonth(dto.getMonth() != null && !dto.getMonth().isEmpty() ? Integer.parseInt(dto.getMonth()) : null);
        entity.setDay(dto.getDay() != null && !dto.getDay().isEmpty() ? Integer.parseInt(dto.getDay()) : null);
        entity.setWeekday(dto.getWeekday());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        return entity;
    }
}
