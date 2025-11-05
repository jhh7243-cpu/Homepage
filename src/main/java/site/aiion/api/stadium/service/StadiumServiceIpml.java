package site.aiion.api.stadium.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.stadium.domain.StadiumDTO;
import site.aiion.api.stadium.domain.Stadium;
import site.aiion.api.stadium.repository.StadiumRepository;

@Service
@RequiredArgsConstructor
public class StadiumServiceIpml implements StadiumService {

    private final StadiumRepository stadiumRepository;

    private StadiumDTO entityToDTO(Stadium entity) {
        return StadiumDTO.builder()
                .id(entity.getId())
                .stadium_Uk(entity.getStadium_Uk())
                .stadium_name(entity.getStadium_name())
                .hometeam_Uk(entity.getHometeam_Uk())
                .seat_count(entity.getSeat_count())
                .address(entity.getAddress())
                .ddd(entity.getDdd())
                .tel(entity.getTel())
                .build();
    }

    @Override
    public Messenger findById(StadiumDTO stadiumDTO) {
        Optional<Stadium> entity = stadiumRepository.findById(stadiumDTO.getId());
        if (entity.isPresent()) {
            StadiumDTO dto = entityToDTO(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("경기장을 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    public Messenger findAll() {
        List<Stadium> entities = stadiumRepository.findAll();
        List<StadiumDTO> dtoList = entities.stream()
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
    public Messenger save(StadiumDTO stadiumDTO) {
        Stadium entity = Stadium.builder()
                .stadium_Uk(stadiumDTO.getStadium_Uk())
                .stadium_name(stadiumDTO.getStadium_name())
                .hometeam_Uk(stadiumDTO.getHometeam_Uk())
                .seat_count(stadiumDTO.getSeat_count())
                .address(stadiumDTO.getAddress())
                .ddd(stadiumDTO.getDdd())
                .tel(stadiumDTO.getTel())
                .build();
        
        Stadium saved = stadiumRepository.save(entity);
        StadiumDTO dto = entityToDTO(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: " + saved.getStadium_Uk())
                .data(dto)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<StadiumDTO> stadiumDTOList) {
        List<Stadium> entities = stadiumDTOList.stream()
                .map(dto -> Stadium.builder()
                        .stadium_Uk(dto.getStadium_Uk())
                        .stadium_name(dto.getStadium_name())
                        .hometeam_Uk(dto.getHometeam_Uk())
                        .seat_count(dto.getSeat_count())
                        .address(dto.getAddress())
                        .ddd(dto.getDdd())
                        .tel(dto.getTel())
                        .build())
                .collect(Collectors.toList());
        
        List<Stadium> saved = stadiumRepository.saveAll(entities);
        return Messenger.builder()
                .Code(200)
                .message("일괄 저장 성공: " + saved.size() + "개")
                .build();
    }

    @Override
    @Transactional
    public Messenger update(StadiumDTO stadiumDTO) {
        Optional<Stadium> optionalEntity = stadiumRepository.findById(stadiumDTO.getId());
        if (optionalEntity.isPresent()) {
            Stadium entity = optionalEntity.get();
            entity.setStadium_name(stadiumDTO.getStadium_name());
            entity.setHometeam_Uk(stadiumDTO.getHometeam_Uk());
            entity.setSeat_count(stadiumDTO.getSeat_count());
            entity.setAddress(stadiumDTO.getAddress());
            entity.setDdd(stadiumDTO.getDdd());
            entity.setTel(stadiumDTO.getTel());
            
            Stadium saved = stadiumRepository.save(entity);
            StadiumDTO dto = entityToDTO(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: " + stadiumDTO.getStadium_Uk())
                    .data(dto)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("수정할 경기장을 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public Messenger delete(StadiumDTO stadiumDTO) {
        Optional<Stadium> optionalEntity = stadiumRepository.findById(stadiumDTO.getId());
        if (optionalEntity.isPresent()) {
            stadiumRepository.deleteById(stadiumDTO.getId());
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: " + stadiumDTO.getId())
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 경기장을 찾을 수 없습니다.")
                    .build();
        }
    }

}

