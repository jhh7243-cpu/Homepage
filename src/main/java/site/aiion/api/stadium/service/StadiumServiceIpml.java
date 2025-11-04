package site.aiion.api.stadium.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.stadium.domain.StadiumDTO;
import site.aiion.api.stadium.domain.StadiumEntity;
import site.aiion.api.stadium.domain.StadiumVO;
import site.aiion.api.stadium.repository.StadiumRepository;

@Service
@RequiredArgsConstructor
public class StadiumServiceIpml implements StadiumService {

    private final StadiumRepository stadiumRepository;

    private StadiumVO entityToVO(StadiumEntity entity) {
        return StadiumVO.builder()
                .stadiumId(entity.getStadiumId())
                .stadiumName(entity.getStadiumName())
                .hometeamId(entity.getHometeamId())
                .seatCount(entity.getSeatCount())
                .address(entity.getAddress())
                .ddd(entity.getDdd())
                .tel(entity.getTel())
                .build();
    }

    @Override
    public Messenger findById(StadiumDTO stadiumDTO) {
        Optional<StadiumEntity> entity = stadiumRepository.findById(stadiumDTO.getStadiumId());
        if (entity.isPresent()) {
            StadiumVO vo = entityToVO(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(vo)
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
        List<StadiumEntity> entities = stadiumRepository.findAll();
        List<StadiumVO> voList = entities.stream()
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
    public Messenger save(StadiumDTO stadiumDTO) {
        StadiumEntity entity = StadiumEntity.builder()
                .stadiumId(stadiumDTO.getStadiumId())
                .stadiumName(stadiumDTO.getStadiumName())
                .hometeamId(stadiumDTO.getHometeamId())
                .seatCount(stadiumDTO.getSeatCount())
                .address(stadiumDTO.getAddress())
                .ddd(stadiumDTO.getDdd())
                .tel(stadiumDTO.getTel())
                .build();
        
        StadiumEntity saved = stadiumRepository.save(entity);
        StadiumVO vo = entityToVO(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: " + saved.getStadiumId())
                .data(vo)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<StadiumDTO> stadiumDTOList) {
        List<StadiumEntity> entities = stadiumDTOList.stream()
                .map(dto -> StadiumEntity.builder()
                        .stadiumId(dto.getStadiumId())
                        .stadiumName(dto.getStadiumName())
                        .hometeamId(dto.getHometeamId())
                        .seatCount(dto.getSeatCount())
                        .address(dto.getAddress())
                        .ddd(dto.getDdd())
                        .tel(dto.getTel())
                        .build())
                .collect(Collectors.toList());
        
        List<StadiumEntity> saved = stadiumRepository.saveAll(entities);
        return Messenger.builder()
                .Code(200)
                .message("일괄 저장 성공: " + saved.size() + "개")
                .build();
    }

    @Override
    @Transactional
    public Messenger update(StadiumDTO stadiumDTO) {
        Optional<StadiumEntity> optionalEntity = stadiumRepository.findById(stadiumDTO.getStadiumId());
        if (optionalEntity.isPresent()) {
            StadiumEntity entity = optionalEntity.get();
            entity.setStadiumName(stadiumDTO.getStadiumName());
            entity.setHometeamId(stadiumDTO.getHometeamId());
            entity.setSeatCount(stadiumDTO.getSeatCount());
            entity.setAddress(stadiumDTO.getAddress());
            entity.setDdd(stadiumDTO.getDdd());
            entity.setTel(stadiumDTO.getTel());
            
            StadiumEntity saved = stadiumRepository.save(entity);
            StadiumVO vo = entityToVO(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: " + stadiumDTO.getStadiumId())
                    .data(vo)
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
        Optional<StadiumEntity> optionalEntity = stadiumRepository.findById(stadiumDTO.getStadiumId());
        if (optionalEntity.isPresent()) {
            stadiumRepository.deleteById(stadiumDTO.getStadiumId());
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: " + stadiumDTO.getStadiumId())
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 경기장을 찾을 수 없습니다.")
                    .build();
        }
    }

}

