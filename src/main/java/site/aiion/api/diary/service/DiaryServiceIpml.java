package site.aiion.api.diary.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.diary.domain.DiaryDTO;
import site.aiion.api.diary.domain.DiaryEntity;
import site.aiion.api.diary.domain.DiaryVO;
import site.aiion.api.diary.repository.DiaryRepository;

@Service
@RequiredArgsConstructor
public class DiaryServiceIpml implements DiaryService{

    private final DiaryRepository diaryRepository;

    private DiaryVO entityToVO(DiaryEntity entity) {
        return DiaryVO.builder()
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
        Optional<DiaryEntity> entity = diaryRepository.findById(diaryDTO.getDiary_Id());
        if (entity.isPresent()) {
            DiaryVO vo = entityToVO(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(vo)
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
        List<DiaryEntity> entities = diaryRepository.findAll();
        List<DiaryVO> voList = entities.stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());
        return Messenger.builder()
                .Code(200)
                .message("전체 조회 성공: " + voList.size() + "개")
                .data(voList)
                .build();
    }

    @Override
    @Transactional
    public Messenger save(DiaryDTO diaryDTO) {
        DiaryEntity entity = DiaryEntity.builder()
                .year(diaryDTO.getYear())
                .month(diaryDTO.getMonth())
                .day(diaryDTO.getDay())
                .weekday(diaryDTO.getWeekday())
                .title(diaryDTO.getTitle())
                .content(diaryDTO.getContent())
                .build();
        
        DiaryEntity saved = diaryRepository.save(entity);
        DiaryVO vo = entityToVO(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: ID " + saved.getDiary_Id())
                .data(vo)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<DiaryDTO> diaryDTOList) {
        List<DiaryEntity> entities = diaryDTOList.stream()
                .map(dto -> DiaryEntity.builder()
                        .year(dto.getYear())
                        .month(dto.getMonth())
                        .day(dto.getDay())
                        .weekday(dto.getWeekday())
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .build())
                .collect(Collectors.toList());
        
        List<DiaryEntity> saved = diaryRepository.saveAll(entities);
        return Messenger.builder()
                .Code(200)
                .message("일괄 저장 성공: " + saved.size() + "개")
                .build();
    }

    @Override
    @Transactional
    public Messenger update(DiaryDTO diaryDTO) {
        Optional<DiaryEntity> optionalEntity = diaryRepository.findById(diaryDTO.getDiary_Id());
        if (optionalEntity.isPresent()) {
            DiaryEntity entity = optionalEntity.get();
            entity.setYear(diaryDTO.getYear());
            entity.setMonth(diaryDTO.getMonth());
            entity.setDay(diaryDTO.getDay());
            entity.setWeekday(diaryDTO.getWeekday());
            entity.setTitle(diaryDTO.getTitle());
            entity.setContent(diaryDTO.getContent());
            
            DiaryEntity saved = diaryRepository.save(entity);
            DiaryVO vo = entityToVO(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: ID " + diaryDTO.getDiary_Id())
                    .data(vo)
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
        Optional<DiaryEntity> optionalEntity = diaryRepository.findById(diaryDTO.getDiary_Id());
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
