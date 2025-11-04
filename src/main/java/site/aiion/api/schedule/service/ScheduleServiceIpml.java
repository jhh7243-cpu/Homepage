package site.aiion.api.schedule.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.schedule.domain.ScheduleDTO;
import site.aiion.api.schedule.domain.ScheduleEntity;
import site.aiion.api.schedule.domain.ScheduleVO;
import site.aiion.api.schedule.repository.ScheduleRepository;

@Service
@RequiredArgsConstructor
public class ScheduleServiceIpml implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private ScheduleVO entityToVO(ScheduleEntity entity) {
        return ScheduleVO.builder()
                .scheDate(entity.getScheDate())
                .stadiumId(entity.getStadiumId())
                .gubun(entity.getGubun())
                .hometeamId(entity.getHometeamId())
                .awayteamId(entity.getAwayteamId())
                .homeScore(entity.getHomeScore())
                .awayScore(entity.getAwayScore())
                .build();
    }

    @Override
    public Messenger findById(ScheduleDTO scheduleDTO) {
        ScheduleDTO id = new ScheduleDTO(scheduleDTO.getScheDate(), scheduleDTO.getStadiumId(), null, null, null, null, null);
        Optional<ScheduleEntity> entity = scheduleRepository.findById(id);
        if (entity.isPresent()) {
            ScheduleVO vo = entityToVO(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(vo)
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
        List<ScheduleEntity> entities = scheduleRepository.findAll();
        List<ScheduleVO> voList = entities.stream()
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
    public Messenger save(ScheduleDTO scheduleDTO) {
        ScheduleEntity entity = ScheduleEntity.builder()
                .scheDate(scheduleDTO.getScheDate())
                .stadiumId(scheduleDTO.getStadiumId())
                .gubun(scheduleDTO.getGubun())
                .hometeamId(scheduleDTO.getHometeamId())
                .awayteamId(scheduleDTO.getAwayteamId())
                .homeScore(scheduleDTO.getHomeScore())
                .awayScore(scheduleDTO.getAwayScore())
                .build();
        
        ScheduleEntity saved = scheduleRepository.save(entity);
        ScheduleVO vo = entityToVO(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: " + saved.getScheDate() + " " + saved.getStadiumId())
                .data(vo)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<ScheduleDTO> scheduleDTOList) {
        List<ScheduleEntity> entities = scheduleDTOList.stream()
                .map(dto -> ScheduleEntity.builder()
                        .scheDate(dto.getScheDate())
                        .stadiumId(dto.getStadiumId())
                        .gubun(dto.getGubun())
                        .hometeamId(dto.getHometeamId())
                        .awayteamId(dto.getAwayteamId())
                        .homeScore(dto.getHomeScore())
                        .awayScore(dto.getAwayScore())
                        .build())
                .collect(Collectors.toList());
        
        List<ScheduleEntity> saved = scheduleRepository.saveAll(entities);
        List<ScheduleVO> voList = saved.stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());
        return Messenger.builder()
                .Code(200)
                .message("일괄 저장 성공: " + voList.size() + "개")
                .data(voList)
                .build();
    }

    @Override
    @Transactional
    public Messenger update(ScheduleDTO scheduleDTO) {
        ScheduleDTO id = new ScheduleDTO(scheduleDTO.getScheDate(), scheduleDTO.getStadiumId(), null, null, null, null, null);
        Optional<ScheduleEntity> optionalEntity = scheduleRepository.findById(id);
        if (optionalEntity.isPresent()) {
            ScheduleEntity entity = optionalEntity.get();
            entity.setGubun(scheduleDTO.getGubun());
            entity.setHometeamId(scheduleDTO.getHometeamId());
            entity.setAwayteamId(scheduleDTO.getAwayteamId());
            entity.setHomeScore(scheduleDTO.getHomeScore());
            entity.setAwayScore(scheduleDTO.getAwayScore());
            
            ScheduleEntity saved = scheduleRepository.save(entity);
            ScheduleVO vo = entityToVO(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: " + scheduleDTO.getScheDate() + " " + scheduleDTO.getStadiumId())
                    .data(vo)
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
        ScheduleDTO id = new ScheduleDTO(scheduleDTO.getScheDate(), scheduleDTO.getStadiumId(), null, null, null, null, null);
        Optional<ScheduleEntity> optionalEntity = scheduleRepository.findById(id);
        if (optionalEntity.isPresent()) {
            scheduleRepository.deleteById(id);
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: " + scheduleDTO.getScheDate() + " " + scheduleDTO.getStadiumId())
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 일정을 찾을 수 없습니다.")
                    .build();
        }
    }

}
