package site.aiion.api.calendar.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.api.calendar.domain.Calender;
import site.aiion.api.calendar.domain.CalenderDTO;
import site.aiion.api.calendar.repository.CalenderRepository;
import site.aiion.api.common.domain.Messenger;

@Service
@RequiredArgsConstructor
public class CalenderServiceIpml implements CalenderService {

    private final CalenderRepository calenderRepository;

    private CalenderDTO entityToDTO(Calender entity) {
        return CalenderDTO.builder()
                .id(entity.getId())
                .year(entity.getYear())
                .month(entity.getMonth())
                .day(entity.getDay())
                .weekday(entity.getWeekday())
                .title(entity.getTitle())
                .content(entity.getContent())
                .build();
    }

    @Override
    public Messenger findById(CalenderDTO calenderDTO) {
        Optional<Calender> entity = calenderRepository.findById(calenderDTO.getId());
        if (entity.isPresent()) {
            CalenderDTO dto = entityToDTO(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("캘린더를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    public Messenger findAll() {
        List<Calender> entities = calenderRepository.findAll();
        List<CalenderDTO> dtoList = entities.stream()
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
    public Messenger save(CalenderDTO calenderDTO) {
        Calender entity = Calender.builder()
                .year(calenderDTO.getYear())
                .month(calenderDTO.getMonth())
                .day(calenderDTO.getDay())
                .weekday(calenderDTO.getWeekday())
                .title(calenderDTO.getTitle())
                .content(calenderDTO.getContent())
                .build();
        
        Calender saved = calenderRepository.save(entity);
        CalenderDTO dto = entityToDTO(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: ID " + saved.getId())
                .data(dto)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<CalenderDTO> calenderDTOList) {
        List<Calender> entities = calenderDTOList.stream()
                .map(dto -> Calender.builder()
                        .year(dto.getYear())
                        .month(dto.getMonth())
                        .day(dto.getDay())
                        .weekday(dto.getWeekday())
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .build())
                .collect(Collectors.toList());
        
        List<Calender> saved = calenderRepository.saveAll(entities);
        return Messenger.builder()
                .Code(200)
                .message("일괄 저장 성공: " + saved.size() + "개")
                .build();
    }

    @Override
    @Transactional
    public Messenger update(CalenderDTO calenderDTO) {
        Optional<Calender> optionalEntity = calenderRepository.findById(calenderDTO.getId());
        if (optionalEntity.isPresent()) {
            Calender entity = optionalEntity.get();
            entity.setYear(calenderDTO.getYear());
            entity.setMonth(calenderDTO.getMonth());
            entity.setDay(calenderDTO.getDay());
            entity.setWeekday(calenderDTO.getWeekday());
            entity.setTitle(calenderDTO.getTitle());
            entity.setContent(calenderDTO.getContent());
            
            Calender saved = calenderRepository.save(entity);
            CalenderDTO dto = entityToDTO(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: ID " + calenderDTO.getId())
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("수정할 캘린더를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public Messenger delete(CalenderDTO calenderDTO) {
        Optional<Calender> optionalEntity = calenderRepository.findById(calenderDTO.getId());
        if (optionalEntity.isPresent()) {
            calenderRepository.deleteById(calenderDTO.getId());
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: ID " + calenderDTO.getId())
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 캘린더를 찾을 수 없습니다.")
                    .build();
        }
    }
}

