package site.aiion.api.schedule.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.schedule.domain.ScheduleDTO;
import site.aiion.api.schedule.domain.Schedule;
import site.aiion.api.schedule.repository.ScheduleRepository;

@Service
@RequiredArgsConstructor
public class ScheduleServiceIpml implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private ScheduleDTO entityToDTO(Schedule entity) {
        ScheduleDTO dto = ScheduleDTO.builder()
                .id(entity.getId())
                .sche_date(entity.getSche_date())
                .stadium_Uk(entity.getStadium_Uk())
                .gubun(entity.getGubun())
                .hometeam_Uk(entity.getHometeam_Uk())
                .awayteam_Uk(entity.getAwayteam_Uk())
                .home_score(entity.getHome_score())
                .away_score(entity.getAway_score())
                .build();
        return dto;
    }

    @Override
    public Messenger findById(ScheduleDTO scheduleDTO) {
        Optional<Schedule> entity = scheduleRepository.findById(scheduleDTO.getId());
        if (entity.isPresent()) {
            ScheduleDTO dto = entityToDTO(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("일정을 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    public Messenger findAll() {
        List<Schedule> entities = scheduleRepository.findAll();
        List<ScheduleDTO> dtoList = entities.stream()
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
    public Messenger save(ScheduleDTO scheduleDTO) {
        Schedule entity = Schedule.builder()
                .sche_date(scheduleDTO.getSche_date())
                .stadium_Uk(scheduleDTO.getStadium_Uk())
                .gubun(scheduleDTO.getGubun())
                .hometeam_Uk(scheduleDTO.getHometeam_Uk())
                .awayteam_Uk(scheduleDTO.getAwayteam_Uk())
                .home_score(scheduleDTO.getHome_score())
                .away_score(scheduleDTO.getAway_score())
                .build();
        
        Schedule saved = scheduleRepository.save(entity);
        ScheduleDTO dto = entityToDTO(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: " + saved.getId())
                .data(dto)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<ScheduleDTO> scheduleDTOList) {
        List<Schedule> entities = scheduleDTOList.stream()
                .map(dto -> Schedule.builder()
                        .sche_date(dto.getSche_date())
                        .stadium_Uk(dto.getStadium_Uk())
                        .gubun(dto.getGubun())
                        .hometeam_Uk(dto.getHometeam_Uk())
                        .awayteam_Uk(dto.getAwayteam_Uk())
                        .home_score(dto.getHome_score())
                        .away_score(dto.getAway_score())
                        .build())
                .collect(Collectors.toList());
        
        List<Schedule> saved = scheduleRepository.saveAll(entities);
        List<ScheduleDTO> dtoList = saved.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
        return Messenger.builder()
                .Code(200)
                .message("일괄 저장 성공: " + dtoList.size() + "개")
                .data(dtoList)
                .build();
    }

    @Override
    @Transactional
    public Messenger update(ScheduleDTO scheduleDTO) {
        Optional<Schedule> optionalEntity = scheduleRepository.findById(scheduleDTO.getId());
        if (optionalEntity.isPresent()) {
            Schedule entity = optionalEntity.get();
            entity.setGubun(scheduleDTO.getGubun());
            entity.setHometeam_Uk(scheduleDTO.getHometeam_Uk());
            entity.setAwayteam_Uk(scheduleDTO.getAwayteam_Uk());
            entity.setHome_score(scheduleDTO.getHome_score());
            entity.setAway_score(scheduleDTO.getAway_score());
            
            Schedule saved = scheduleRepository.save(entity);
            ScheduleDTO dto = entityToDTO(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: " + scheduleDTO.getId())
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("수정할 일정을 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public Messenger delete(ScheduleDTO scheduleDTO) {
        Optional<Schedule> optionalEntity = scheduleRepository.findById(scheduleDTO.getId());
        if (optionalEntity.isPresent()) {
            scheduleRepository.deleteById(scheduleDTO.getId());
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: " + scheduleDTO.getId())
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 일정을 찾을 수 없습니다.")
                    .build();
        }
    }

}
