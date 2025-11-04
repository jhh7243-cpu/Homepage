package site.aiion.api.player.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.api.common.domain.Messenger;
import site.aiion.api.player.domain.PlayerDTO;
import site.aiion.api.player.domain.PlayerEntity;
import site.aiion.api.player.domain.PlayerVO;
import site.aiion.api.player.repository.PlayerRepository;

@Service
@RequiredArgsConstructor
public class PlayerServiceIpml implements PlayerService {

    private final PlayerRepository playerRepository;

    private PlayerVO entityToVO(PlayerEntity entity) {
        return PlayerVO.builder()
                .playerId(entity.getPlayerId())
                .playerName(entity.getPlayerName())
                .ePlayerName(entity.getEPlayerName())
                .nickname(entity.getNickname())
                .joinYyyy(entity.getJoinYyyy())
                .position(entity.getPosition())
                .backNo(entity.getBackNo())
                .nation(entity.getNation())
                .birthDate(entity.getBirthDate())
                .solar(entity.getSolar())
                .height(entity.getHeight())
                .weight(entity.getWeight())
                .teamId(entity.getTeamId())
                .build();
    }

    @Override
    public Messenger findById(PlayerDTO playerDTO) {
        Optional<PlayerEntity> entity = playerRepository.findById(playerDTO.getPlayerId());
        if (entity.isPresent()) {
            PlayerVO vo = entityToVO(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(vo)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("선수를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    public Messenger findAll() {
        List<PlayerEntity> entities = playerRepository.findAll();
        List<PlayerVO> voList = entities.stream()
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
    public Messenger save(PlayerDTO playerDTO) {
        PlayerEntity entity = PlayerEntity.builder()
                .playerId(playerDTO.getPlayerId())
                .playerName(playerDTO.getPlayerName())
                .ePlayerName(playerDTO.getEPlayerName())
                .nickname(playerDTO.getNickname())
                .joinYyyy(playerDTO.getJoinYyyy())
                .position(playerDTO.getPosition())
                .backNo(playerDTO.getBackNo())
                .nation(playerDTO.getNation())
                .birthDate(playerDTO.getBirthDate())
                .solar(playerDTO.getSolar())
                .height(playerDTO.getHeight())
                .weight(playerDTO.getWeight())
                .teamId(playerDTO.getTeamId())
                .build();
        
        PlayerEntity saved = playerRepository.save(entity);
        PlayerVO vo = entityToVO(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: " + saved.getPlayerId())
                .data(vo)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<PlayerDTO> playerDTOList) {
        List<PlayerEntity> entities = playerDTOList.stream()
                .map(dto -> PlayerEntity.builder()
                        .playerId(dto.getPlayerId())
                        .playerName(dto.getPlayerName())
                        .ePlayerName(dto.getEPlayerName())
                        .nickname(dto.getNickname())
                        .joinYyyy(dto.getJoinYyyy())
                        .position(dto.getPosition())
                        .backNo(dto.getBackNo())
                        .nation(dto.getNation())
                        .birthDate(dto.getBirthDate())
                        .solar(dto.getSolar())
                        .height(dto.getHeight())
                        .weight(dto.getWeight())
                        .teamId(dto.getTeamId())
                        .build())
                .collect(Collectors.toList());
        
        List<PlayerEntity> saved = playerRepository.saveAll(entities);
        return Messenger.builder()
                .Code(200)
                .message("일괄 저장 성공: " + saved.size() + "개")
                .build();
    }

    @Override
    @Transactional
    public Messenger update(PlayerDTO playerDTO) {
        Optional<PlayerEntity> optionalEntity = playerRepository.findById(playerDTO.getPlayerId());
        if (optionalEntity.isPresent()) {
            PlayerEntity entity = optionalEntity.get();
            entity.setPlayerName(playerDTO.getPlayerName());
            entity.setEPlayerName(playerDTO.getEPlayerName());
            entity.setNickname(playerDTO.getNickname());
            entity.setJoinYyyy(playerDTO.getJoinYyyy());
            entity.setPosition(playerDTO.getPosition());
            entity.setBackNo(playerDTO.getBackNo());
            entity.setNation(playerDTO.getNation());
            entity.setBirthDate(playerDTO.getBirthDate());
            entity.setSolar(playerDTO.getSolar());
            entity.setHeight(playerDTO.getHeight());
            entity.setWeight(playerDTO.getWeight());
            entity.setTeamId(playerDTO.getTeamId());
            
            PlayerEntity saved = playerRepository.save(entity);
            PlayerVO vo = entityToVO(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: " + playerDTO.getPlayerId())
                    .data(vo)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("수정할 선수를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public Messenger delete(PlayerDTO playerDTO) {
        Optional<PlayerEntity> optionalEntity = playerRepository.findById(playerDTO.getPlayerId());
        if (optionalEntity.isPresent()) {
            playerRepository.deleteById(playerDTO.getPlayerId());
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: " + playerDTO.getPlayerId())
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 선수를 찾을 수 없습니다.")
                    .build();
        }
    }

}
