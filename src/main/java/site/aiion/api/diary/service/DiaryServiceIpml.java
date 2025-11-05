package site.aiion.api.diary.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.diary.domain.DiaryDTO;
import site.aiion.api.diary.domain.Diary;
import site.aiion.api.diary.repository.DiaryRepository;

@Service
@RequiredArgsConstructor
public class DiaryServiceIpml implements DiaryService{

    private final DiaryRepository diaryRepository;

    private DiaryDTO entityToDTO(Diary entity) {
        return DiaryDTO.builder()
                .diary_Id(entity.getDiary_Id())
                .year(entity.getYear())
                .month(entity.getMonth())
                .day(entity.getDay())
                .weekday(entity.getWeekday())
                .title(entity.getTitle())
                .content(entity.getContent())
                .build();
    }

    @Override
    public Messenger findById(DiaryDTO diaryDTO) {
        Optional<Diary> entity = diaryRepository.findById(diaryDTO.getDiary_Id());
        if (entity.isPresent()) {
            DiaryDTO dto = entityToDTO(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("일기를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    public Messenger findAll() {
        List<Diary> entities = diaryRepository.findAll();
        List<DiaryDTO> dtoList = entities.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
        return Messenger.builder()
                .Code(200)
                .message("전체 조회 성공: " + dtoList.size() + "개")
                .data(dtoList)
                .build();
    }

    @Override
    @Transactional
    public Messenger save(DiaryDTO diaryDTO) {
        Diary entity = Diary.builder()
                .year(diaryDTO.getYear())
                .month(diaryDTO.getMonth())
                .day(diaryDTO.getDay())
                .weekday(diaryDTO.getWeekday())
                .title(diaryDTO.getTitle())
                .content(diaryDTO.getContent())
                .build();
        
        Diary saved = diaryRepository.save(entity);
        DiaryDTO dto = entityToDTO(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: ID " + saved.getDiary_Id())
                .data(dto)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<DiaryDTO> diaryDTOList) {
        List<Diary> entities = diaryDTOList.stream()
                .map(dto -> Diary.builder()
                        .year(dto.getYear())
                        .month(dto.getMonth())
                        .day(dto.getDay())
                        .weekday(dto.getWeekday())
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .build())
                .collect(Collectors.toList());
        
        List<Diary> saved = diaryRepository.saveAll(entities);
        return Messenger.builder()
                .Code(200)
                .message("일괄 저장 성공: " + saved.size() + "개")
                .build();
    }

    @Override
    @Transactional
    public Messenger update(DiaryDTO diaryDTO) {
        Optional<Diary> optionalEntity = diaryRepository.findById(diaryDTO.getDiary_Id());
        if (optionalEntity.isPresent()) {
            Diary entity = optionalEntity.get();
            entity.setYear(diaryDTO.getYear());
            entity.setMonth(diaryDTO.getMonth());
            entity.setDay(diaryDTO.getDay());
            entity.setWeekday(diaryDTO.getWeekday());
            entity.setTitle(diaryDTO.getTitle());
            entity.setContent(diaryDTO.getContent());
            
            Diary saved = diaryRepository.save(entity);
            DiaryDTO dto = entityToDTO(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: ID " + diaryDTO.getDiary_Id())
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("수정할 일기를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public Messenger delete(DiaryDTO diaryDTO) {
        Optional<Diary> optionalEntity = diaryRepository.findById(diaryDTO.getDiary_Id());
        if (optionalEntity.isPresent()) {
            diaryRepository.deleteById(diaryDTO.getDiary_Id());
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: ID " + diaryDTO.getDiary_Id())
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 일기를 찾을 수 없습니다.")
                    .build();
        }
    }


    


}
